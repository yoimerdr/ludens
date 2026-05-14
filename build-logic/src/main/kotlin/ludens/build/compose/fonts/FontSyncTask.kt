package ludens.build.compose.fonts

import ludens.build.compose.configuration.LudensConfiguration
import ludens.build.compose.configuration.LudensLanguageFont
import ludens.build.compose.configuration.resolveActiveLanguages
import ludens.build.compose.language.collectStringNames
import ludens.build.compose.language.getLanguagesFrom
import ludens.build.helpers.parseResourceNameFromFilename
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Synchronizes Compose font resources from the asset store and generates `FontFamily` accessors.
 *
 * The task bridges asset files and generated Compose code. It discovers the active languages,
 * computes the exact font set needed by those languages, copies the required files into Compose
 * resources, removes stale `.ttf` files, and finally writes the generated Kotlin source.
 *
 * Language-specific fallbacks are derived from the active language set and only included when the
 * corresponding font files exist in the asset store. This keeps the generated source aligned
 * with the resource tree and avoids references to missing files.
 */
abstract class FontSyncTask : DefaultTask() {

    /**
     * Source directory containing the base font files in the asset store.
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val assetsFontsDir: DirectoryProperty

    /**
     * Source directory used to detect active language tags for fallback fonts.
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val assetsLanguagesDir: DirectoryProperty

    /**
     * Destination directory inside Compose resources where copied font files are written.
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val resourceFontsDir: DirectoryProperty

    /**
     * Full Ludens configuration used to resolve base and fallback font names.
     */
    @get:Internal
    var configuration: LudensConfiguration = LudensConfiguration()

    /**
     * Package name used for the generated Kotlin source file.
     */
    @get:Input
    abstract val generatedPackage: Property<String>

    /**
     * Output file that will contain the generated font accessors.
     */
    @get:OutputFile
    abstract val generatedFile: RegularFileProperty

    /**
     * Executes the sync and code generation step.
     *
     * The task keeps the copy phase and the code generation phase together so the generated
     * `FontFamily` accessors always match the files already copied into Compose resources.
     */
    @TaskAction
    fun sync() {
        val assetFonts = assetsFontsDir.get().asFile
        val assetLang = assetsLanguagesDir.get().asFile
        val resourceFonts = resourceFontsDir.get().asFile
        val generatedFile = generatedFile.get().asFile

        val fontsConfig = configuration.fonts
        val langConfig = configuration.languages

        // Build the active language list from the asset store and the configured allowlist.
        val discoveredTags = discoverLanguageTags(assetLang)
        val activeLanguages = langConfig.resolveActiveLanguages(discoveredTags)

        val baseFonts = listOfNotNull(
            fontsConfig.display.takeIf { it.isNotBlank() },
            fontsConfig.body.takeIf { it.isNotBlank() },
        )
        val fallbackFonts = activeLanguages.flatMap { tag ->
            fontsConfig.language[tag]?.let { roles ->
                listOfNotNull(
                    roles.display.takeIf { it.isNotBlank() },
                    roles.body.takeIf { it.isNotBlank() },
                )
            } ?: emptyList()
        }.distinct()

        // Only keep the font files that are actually referenced by the active configuration.
        val neededFonts = (baseFonts + fallbackFonts).distinct().toSet()

        if (assetFonts.isDirectory) {
            // Copy required font files into Compose resources.
            neededFonts.forEach { fileName ->
                val source = File(assetFonts, fileName)
                if (source.isFile) {
                    source.copyTo(File(resourceFonts, fileName), overwrite = true)
                    logger.info("Copied font $fileName")
                }
            }

            // Remove stale font files that are no longer referenced by the generated families.
            resourceFonts.listFiles()?.filter { it.isFile && it.name.endsWith(".ttf") }
                ?.forEach { file ->
                    if (file.name !in neededFonts) {
                        file.delete()
                        logger.info("Deleted font ${file.name}")
                    }
                }
        }

        generatedFile.parentFile?.mkdirs()

        // Generate the Kotlin source that exposes the final font families.
        generatedFile.writeText(
            generateFontFamiliesCode(
                fontsConfig.display,
                fontsConfig.body,
                fontsConfig.language,
                activeLanguages,
            )
        )
        logger.info("Generated Fonts.kt")
    }

    /**
     * Discovers the supported language tags from the asset language directory.
     *
     * A language is only considered when it has a directory and the resource naming metadata can
     * map it back to a valid language entry.
     */
    private fun discoverLanguageTags(assetLangDir: File): Set<String> {
        if (!assetLangDir.isDirectory) return emptySet()
        val dirs = assetLangDir.listFiles()?.filter { it.isDirectory } ?: return emptySet()
        return getLanguagesFrom(dirs, collectStringNames(assetLangDir)).map { it.tag }.toSet()
    }

    /**
     * Generates the Kotlin source that exposes the resolved `FontFamily` accessors.
     *
     * The generated file imports only the exact font resources referenced by the selected base
     * fonts and language-specific fallbacks.
     */
    private fun generateFontFamiliesCode(
        displayFont: String,
        bodyFont: String,
        langFonts: Map<String, LudensLanguageFont>,
        activeLanguages: List<String>,
    ): String {

        val bodyFontName = bodyFont.takeIf { it.isNotBlank() }
        val displayFontName = displayFont.takeIf { it.isNotBlank() }

        val bodyFallbacks = activeLanguages.mapNotNull { tag ->
            langFonts[tag]?.body?.takeIf { it.isNotBlank() }
        }.distinct()

        val displayFallbacks = activeLanguages.mapNotNull { tag ->
            langFonts[tag]?.display?.takeIf { it.isNotBlank() }
        }.distinct()

        val families = listOfNotNull(
            bodyFontName?.let { "BodyFont" to FontFamilyEntry(it, bodyFallbacks) },
            displayFontName?.let { "DisplayFont" to FontFamilyEntry(it, displayFallbacks) },
        )

        // Import only the files referenced by the generated families.
        val allFontFiles = families.flatMap { (_, entry) ->
            listOf(entry.base) + entry.fallbacks
        }.distinct().sorted()

        val weights = listOf("Light", "Normal", "Medium", "SemiBold", "Bold", "Black")

        // Emit one font entry per weight so Compose can resolve the family consistently.
        fun fontBlock(resName: String): String = weights.joinToString("") { w ->
            "            Font(Res.font.$resName, weight = FontWeight.$w),\n"
        }

        // Build the Kotlin syntax for a `FontFamily` getter.
        fun familyBlock(entry: FontFamilyEntry): String = buildString {
            val resName = parseResourceNameFromFilename(entry.base)
            appendLine("        FontFamily(")
            append(fontBlock(resName))
            entry.fallbacks.forEach { fb -> append(fontBlock(parseResourceNameFromFilename(fb))) }
            appendLine("        )")
        }

        return buildString {
            appendLine("package ${generatedPackage.get()}")
            appendLine()
            appendLine("// Autogenerated file. Do not edit manually.")
            appendLine()

            appendLine("import androidx.compose.runtime.Composable")
            appendLine("import androidx.compose.runtime.Stable")
            appendLine("import androidx.compose.ui.text.font.FontFamily")
            appendLine("import androidx.compose.ui.text.font.FontWeight")
            appendLine("import ludens.composeapp.generated.resources.Res")
            appendLine("import org.jetbrains.compose.resources.Font")
            appendLine()

            allFontFiles.distinct().forEach { fileName ->
                appendLine("import ludens.composeapp.generated.resources.${parseResourceNameFromFilename(fileName)}")
            }
            appendLine()

            families.forEach { (name, entry) ->
                appendLine("val $name: FontFamily")
                appendLine("    @Stable")
                appendLine("    @Composable")
                appendLine("    get() =")
                append(familyBlock(entry))
                appendLine()
            }
        }
    }

    /**
     * Base font plus language-specific fallback list used to render a `FontFamily` block.
     *
     * The base font is always emitted first. Fallbacks keep the language-specific files in the
     * order they should be considered by Compose.
     */
    private data class FontFamilyEntry(
        val base: String,
        val fallbacks: List<String>,
    )
}

package ludens.build.compose.language

import ludens.build.compose.configuration.LudensLanguageConfiguration
import ludens.build.compose.configuration.resolveActiveLanguages
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Synchronizes language `strings.xml` files from the asset store into Compose resources.
 *
 * The task keeps the resource tree aligned with the currently active language set: it removes
 * previously generated `strings.xml` files, copies the active ones from the asset store, and
 * skips everything else.
 */
abstract class LanguageStringsSyncTask : DefaultTask() {

    /**
     * Source directory that contains the language asset folders.
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val assetLanguagesDir: DirectoryProperty

    /**
     * Destination Compose resources directory where generated language folders are written.
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val resourcesDir: DirectoryProperty

    /**
     * Language allowlist used to decide which discovered translations are copied.
     */
    @get:Internal
    abstract val configuration: Property<LudensLanguageConfiguration>

    /**
     * Executes the sync between asset language files and Compose resources.
     *
     * The task first discovers languages from the asset tree, resolves the active subset, cleans
     * any previously generated output, and then copies the active `strings.xml` files into the
     * correct Compose `values*` directory.
     */
    @TaskAction
    fun sync() {
        val langDir = assetLanguagesDir.get().asFile
        if (!langDir.isDirectory) return

        val configuration = configuration.get()

        val discovered = discoverAssetLanguages(langDir)
        val active = configuration.resolveActiveLanguages(discovered.map { it.tag }.toSet())
        if (active.isEmpty()) return

        val resourcesRoot = resourcesDir.get().asFile
        val defaultTag = active.first()
        val activeSet = active.toSet()


        discovered.forEach { entry ->
            // Remove the previously generated file for this language before rewriting it.
            val androidQualifier = androidQualifier(entry.tag)
            val file = resourcesRoot
                .resolve("values-${androidQualifier}")
                .resolve("strings.xml")

            if (file.exists()) {
                file.delete()
                logger.info("[ludens] Deleted ${file.name} from ${file.parentFile.name}")
            }

            if (entry.tag !in activeSet) return@forEach

            // Copy the active strings.xml file from assets into the matching Compose directory.
            val sourceDir = langDir.resolve(androidQualifier)
            val targetName = if (entry.tag == defaultTag) "values"
            else "values-${androidQualifier}"

            val stringsFile = sourceDir.resolve("strings.xml")

            if (stringsFile.isFile) {
                stringsFile.copyTo(
                    resourcesRoot.resolve(targetName)
                        .resolve("strings.xml"),
                    overwrite = true,
                )
                logger.info("[ludens] Copied ${stringsFile.name} -> $targetName/strings.xml")
            }
        }


    }

    /**
     * Discovers the language directories available in the asset tree.
     *
     * The helper uses the existing language parsing rules and only returns folders that can be
     * mapped to supported language tags.
     */
    private fun discoverAssetLanguages(assetLangDir: File): List<LanguageEntry> {
        if (!assetLangDir.isDirectory) return emptyList()
        val available = assetLangDir.listFiles()?.filter { it.isDirectory } ?: emptyList()
        val allStringNames = collectStringNames(assetLangDir)
        return getLanguagesFrom(available, allStringNames)
    }
}

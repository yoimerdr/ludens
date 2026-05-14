/*
 * Language discovery defined by:
 * https://github.com/rainbowtrash2333
 */

package ludens.build.compose.language


import java.io.File

/**
 * Represents a discovered language with its BCP-47 tag and the resource name
 * used to look up its display label in `strings.xml`.
 */
data class LanguageEntry(
    val tag: String,
    val labelResourceName: String,
)

/**
 * Discovers language tags by scanning Compose `values` directories.
 *
 * The default `values/` directory is treated as English (`en`). Locale
 * directories such as `values-es` and `values-pt-rBR` are converted to
 * BCP-47 tags (`es`, `pt-BR`) and mapped to safe resource names (`es`,
 * `pt_br`). Only languages whose display label resource exists in any
 * `strings.xml` file are included. Results are sorted for stable ordering.
 */
fun discoverLanguages(
    resourceDir: File,
    warn: (String) -> Unit = {},
): List<LanguageEntry> {
    val valuesDirs = resourceDir.listFiles { file ->
        file.isDirectory && (file.name == "values" || file.name.startsWith("values-"))
    } ?: emptyArray()

    val allStringNames = collectStringNames(resourceDir)

    return getLanguagesFrom(
        dirs = valuesDirs.toList(),
        names = allStringNames,
        warn = warn,
    )
}

/**
 * Filters a list of candidate resource directories and returns only those that
 * map to a valid language tag and have a display label defined in [names].
 *
 * Converts directory names such as `values-es` and `values-pt-rBR` into
 * BCP-47 tags (`es`, `pt-BR`) and maps them to safe resource names (`es`,
 * `pt_br`). Handles deduplication by tag and returns results sorted
 * alphabetically.
 */
fun getLanguagesFrom(
    dirs: List<File>,
    names: Set<String>,
    warn: (String) -> Unit = {},
): List<LanguageEntry> {

    return dirs.mapNotNull { dir ->
        val language = languageEntryFor(dir)

        if (!File(dir, "strings.xml").exists()) {
            warn("Language directory ${dir.name} has no strings.xml, skipping")
            null
        } else if (language == null) {
            warn("Resource directory ${dir.name} does not map to a supported language tag, skipping")
            null
        } else if (language.labelResourceName !in names) {
            warn("Language directory ${dir.name} found but label \"${language.labelResourceName}\" for tag \"${language.tag}\" is not defined as a string resource in any strings.xml")
            null
        } else {
            language
        }
    }.distinctBy { it.tag }.sortedBy { it.tag.lowercase() }
}

fun languageEntryFor(dir: File): LanguageEntry? {
    if (dir.name == "values") {
        return LanguageEntry(tag = "en", labelResourceName = "en")
    }

    val qualifier = dir.name.removePrefix("values-")
    val language = parseLanguageQualifier(qualifier) ?: return null
    return LanguageEntry(tag = language, labelResourceName = resourceNameFor(language))
}

fun parseLanguageQualifier(qualifier: String): String? {
    val parts = qualifier.split('-')
    val language = parts.firstOrNull()?.takeIf { it.matches(Regex("[a-z]{2,3}")) } ?: return null

    val region = parts.drop(1).firstNotNullOfOrNull { part ->
        when {
            part.matches(Regex("r[A-Z]{2}")) -> part.drop(1)
            part.matches(Regex("[A-Z]{2}")) -> part
            else -> null
        }
    }

    return if (region == null) language else "$language-$region"
}

fun resourceNameFor(tag: String): String = tag.lowercase().replace('-', '_')

/**
 * Converts a BCP-47 language tag to an Android resource directory qualifier.
 *
 * Android uses an `r` prefix for region codes in resource qualifiers (e.g. `pt-rBR`),
 * while BCP-47 uses a plain hyphen (e.g. `pt-BR`).
 *
 * Examples:
 * - `"en"`       → `"en"`
 * - `"pt-BR"`    → `"pt-rBR"`
 * - `"zh-CN"`    → `"zh-rCN"`
 */
fun androidQualifier(tag: String): String {
    val parts = tag.split("-")
    return if (parts.size == 1) tag else "${parts[0]}-r${parts[1]}"
}

/**
 * Collects all string `name` attributes from all `strings.xml` files in the
 * compose resources directory tree (including the default `values/`).
 */
fun collectStringNames(resourceDir: File): Set<String> {
    val names = mutableSetOf<String>()
    resourceDir.walkTopDown().forEach { file ->
        if (file.isFile && file.name == "strings.xml") {
            val content = file.readText()
            val regex = Regex("""<string\s+name="([^"]+)"[^>]*>""")
            regex.findAll(content).forEach { match ->
                names.add(match.groupValues[1])
            }
        }
    }
    return names
}

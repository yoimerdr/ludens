package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Language selection rules used by build-logic generators.
 *
 * The `available` property controls which discovered languages are considered active when
 * generating language-specific resources and font fallbacks.
 *
 * Accepted values:
 * - `*` to enable every discovered language
 * - a comma-separated allowlist such as `en,es,pt-rBR`
 *
 * Unknown language tags are ignored during resolution.
 */
data class LudensLanguageConfiguration(
    /**
     * Comma-separated list of active language tags, or `*` for all discovered languages.
     */
    val available: String = "*",
) : Serializable

/**
 * Parsed allowlist of language tags from [LudensLanguageConfiguration.available].
 *
 * Empty items are preserved as trimmed strings and duplicates are removed. Resolution logic
 * filters the result against the set of discovered languages before using it.
 */
val LudensLanguageConfiguration.parsedLanguages: List<String>
    get() = available.split(",").map { it.trim() }
        .distinct()

/**
 * Resolves the active language tags from the discovered language set.
 *
 * When [LudensLanguageConfiguration.available] is `*`, the discovered tags are returned in
 * sorted order. Otherwise, only the configured allowlist entries that exist in [discovered]
 * are kept, preserving the allowlist order.
 */
fun LudensLanguageConfiguration.resolveActiveLanguages(discovered: Set<String>): List<String> {
    return if (available == "*") {
        discovered.sorted()
    } else {
        parsedLanguages.filter { it in discovered }
    }
}

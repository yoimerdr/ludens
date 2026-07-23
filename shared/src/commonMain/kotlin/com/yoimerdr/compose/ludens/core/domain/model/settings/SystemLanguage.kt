package com.yoimerdr.compose.ludens.core.domain.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.generated.language.LanguageMetadata

/**
 * Represents the application's language/locale configuration.
 *
 * Languages are auto-discovered from Compose resource directories. The default
 * `values/` directory is English, while locale directories such as `values-es`
 * and `values-pt-rBR` become BCP-47 tags such as `es` and `pt-BR`.
 *
 * @property tag The BCP-47 language tag (empty string for System option).
 */
@Immutable
data class SystemLanguage(val tag: String) {

    /** Integer value used for persistence compatibility. */
    val value: Int by lazy { entries.indexOf(this).coerceAtLeast(0) }

    companion object {
        /** System-based language (uses device language). */
        val System = SystemLanguage("")

        /** English language (convenience accessor). */
        val English: SystemLanguage by lazy { from("en")!! }

        /** Spanish language (convenience accessor). */
        val Spanish: SystemLanguage by lazy { from("es")!! }

        /** All selectable languages (System + auto-discovered). */
        val entries: List<SystemLanguage> by lazy {
            listOf(System) + LanguageMetadata.availableLanguages.map { SystemLanguage(it) }
        }

        /** Fast tag-to-SystemLanguage lookup. */
        private val byTag: Map<String, SystemLanguage> by lazy {
            entries.associateBy { it.tag }
        }

        /**
         * Retrieves a SystemLanguage from its integer value.
         *
         * @param value The integer value (index in [entries]).
         * @return The corresponding SystemLanguage, or [System] if out of range.
         */
        fun from(value: Int): SystemLanguage =
            entries.getOrElse(value) { System }

        /**
         * Retrieves a SystemLanguage from its language tag.
         *
         * @param tag The BCP-47 language tag.
         * @return The corresponding SystemLanguage, or null if no match is found.
         */
        fun from(tag: String): SystemLanguage? =
            byTag[tag]

        /**
         * Retrieves a SystemLanguage from a locale string.
         *
         * Attempts to match the normalized full locale first, then falls back
         * to the language portion.
         *
         * @param locale The locale string (e.g., "en-US", "es_ES").
         * @return The corresponding SystemLanguage, or null if no match is found.
         */
        fun fromLocaleTag(locale: String): SystemLanguage? {
            val parts = locale
                .replace('_', '-')
                .split('-')
                .filter { it.isNotBlank() }

            if (parts.isEmpty()) {
                return null
            }

            val language = parts.first().lowercase()
            val normalized = buildList {
                add(parts.joinToString("-"))
                if (parts.size >= 2) {
                    add("$language-${parts[1].uppercase()}")
                }
                add(language)
            }

            return normalized.firstNotNullOfOrNull { from(it) }
        }
    }
}

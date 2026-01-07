package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents the application's language/locale configuration.
 *
 * @property value The integer value representing the language option.
 * @property tag The ISO 639-1 language code (empty for System option).
 */
enum class SystemLanguage(
    val value: Int,
    val tag: String,
) {
    /** System-based language (uses device language). */
    System(0, ""),

    /** English language. */
    English(1, "en"),

    /** Spanish language. */
    Spanish(2, "es");

    companion object {
        /**
         * Retrieves a SystemLanguage from its integer value.
         *
         * @param value The integer value to convert.
         * @return The corresponding SystemLanguage.
         * @throws NoSuchElementException if no matching language is found.
         */
        fun from(value: Int): SystemLanguage = entries.first { value == it.value }

        /**
         * Retrieves a SystemLanguage from its language tag.
         *
         * @param tag The language tag to convert.
         * @return The corresponding SystemLanguage, or null if no match is found.
         */
        fun from(tag: String): SystemLanguage? = entries.firstOrNull { tag == it.tag }

        /**
         * Retrieves a SystemLanguage from a locale string.
         *
         * Attempts to match the full locale first, then falls back to the language portion.
         *
         * @param locale The locale string to convert (e.g., "en-US", "es_ES").
         * @return The corresponding SystemLanguage, or null if no match is found.
         */
        fun fromLocaleTag(locale: String): SystemLanguage? {
            return from(locale) ?: from(locale.split('-', '_').first())
        }
    }
}
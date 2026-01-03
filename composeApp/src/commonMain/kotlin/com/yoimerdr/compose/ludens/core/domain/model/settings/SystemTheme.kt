package com.yoimerdr.compose.ludens.core.domain.model.settings


/**
 * Represents the application's theme configuration.
 *
 * @property value The integer value representing the theme option.
 */
enum class SystemTheme(val value: Int) {
    /** Light theme mode. */
    Light(0),

    /** Dark theme mode. */
    Dark(1),

    /** System-based theme mode. */
    System(2);

    companion object {
        /**
         * Retrieves an SystemTheme from its integer value.
         *
         * @param value The integer value to convert.
         * @return The corresponding SystemTheme.
         * @throws NoSuchElementException if no matching type is found.
         */
        fun from(value: Int): SystemTheme = entries.first { value == it.value }
    }
}
package com.yoimerdr.compose.ludens.features.settings.presentation.state

/**
 * Independent configuration categories.
 *
 * Each category represents a distinct group of settings that can be managed separately.
 */
enum class SettingsCategory {
    /** Control settings. */
    Controls,

    /** Tool settings. */
    Tools,

    /** System settings. */
    System,

    /** Action settings. */
    Actions;

    companion object {
        /**
         * Set containing all settings categories.
         */
        val All: Set<SettingsCategory> = entries.toSet()

        /**
         * Creates a set containing all categories except the specified ones.
         *
         * @param category The first category to exclude.
         * @param categories Additional categories to exclude.
         * @return A set containing all categories except the specified ones.
         *
         */
        fun excepting(category: SettingsCategory, vararg categories: SettingsCategory): Set<SettingsCategory> {
            val excluded = setOf(category) + categories
            return All - excluded
        }
    }
}
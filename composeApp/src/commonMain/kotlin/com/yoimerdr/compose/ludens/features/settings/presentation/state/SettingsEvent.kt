package com.yoimerdr.compose.ludens.features.settings.presentation.state

/**
 * Events for the settings screen.
 */
sealed interface SettingsEvent {
    /**
     * Selects a settings section.
     *
     * @param section The section to select.
     */
    data class OnSelectSection(val section: SettingsSection) : SettingsEvent

    /**
     * Resets all settings to default values.
     */
    data object RestoreDefaultSettings : SettingsEvent
}


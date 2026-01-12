package com.yoimerdr.compose.ludens.features.settings.presentation.state

/**
 * Events for the settings screen.
 */
sealed interface SettingsEvent {

    /**
     * Resets all settings to default values.
     */
    data object RestoreDefaultSettings : SettingsEvent
}


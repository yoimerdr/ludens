package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme

/**
 * Base interface for system settings events.
 */
sealed interface SystemSettingsEvent : SettingsEvent {
    /**
     * Event indicating an update to system settings.
     * */
    sealed interface UpdateSettings : SystemSettingsEvent,
        SettingsEvent.UpdateSettings
}

/**
 * Changes the application language.
 *
 * @param language The language to apply.
 */
data class OnChangeLanguage(val language: SystemLanguage) : SystemSettingsEvent.UpdateSettings

/**
 * Changes the application theme.
 *
 * @param theme The theme to apply.
 */
data class OnChangeTheme(val theme: SystemTheme) : SystemSettingsEvent.UpdateSettings

/**
 * Resets all settings to default values.
 */
data object RestoreDefaultSettings : SystemSettingsEvent


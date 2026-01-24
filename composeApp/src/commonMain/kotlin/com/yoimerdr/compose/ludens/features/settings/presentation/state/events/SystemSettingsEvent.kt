package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme

/**
 * Base interface for system settings events.
 */
sealed interface SystemSettingsEvent : SettingsEvent

/**
 * Changes the application language.
 *
 * @param language The language to apply.
 */
data class OnChangeLanguage(val language: SystemLanguage) : SystemSettingsEvent

/**
 * Changes the application theme.
 *
 * @param theme The theme to apply.
 */
data class OnChangeTheme(val theme: SystemTheme) : SystemSettingsEvent

/**
 * Resets all settings to default values.
 */
data object RestoreDefaultSettings : SystemSettingsEvent


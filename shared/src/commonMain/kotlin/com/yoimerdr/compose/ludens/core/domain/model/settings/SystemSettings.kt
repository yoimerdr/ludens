package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents system-level settings for the application.
 *
 * This data class aggregates system-wide configuration options that affect
 * the overall behavior and appearance of the application.
 *
 * @property theme The application's theme configuration (Light, Dark, or System).
 * @property language The application's language/locale setting.
 */
data class SystemSettings(
    val theme: SystemTheme,
    val language: SystemLanguage,
)

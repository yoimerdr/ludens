package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents the complete application settings.
 *
 * This data class aggregates all settings including control settings, tool settings,
 * and theme configuration.
 *
 * @property control The control-related settings (joystick, buttons, positions, etc.).
 * @property tool The tool-related settings (mute, FPS display, etc.).
 * @property theme The application's theme configuration (Light, Dark, or System).
 */
data class Settings(
    val control: ControlSettings,
    val tool: ToolSettings,
    val theme: SystemTheme
)
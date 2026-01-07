package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents the complete application settings.
 *
 * This data class aggregates all settings including control settings, tool settings,
 * and system settings.
 *
 * @property control The control-related settings (joystick, buttons, positions, etc.).
 * @property tool The tool-related settings (mute, FPS display, etc.).
 * @property system The system-level settings (theme, language, etc.).
 */
data class Settings(
    val control: ControlSettings,
    val tool: ToolSettings,
    val system: SystemSettings
)
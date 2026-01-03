package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents the complete application settings.
 *
 * This data class aggregates all settings including control settings, tool settings,
 * and positional information for UI elements.
 *
 * @property control The control-related settings (joystick, buttons, etc.).
 * @property tool The tool-related settings (mute, FPS display, etc.).
 * @property positions The list of positionable items with their coordinates.
 * @property theme The application's theme configuration (Light, Dark, or System).
 */
data class Settings(
    val control: ControlSettings,
    val tool: ToolSettings,
    val positions: List<PositionableItem>,
    val theme: SystemTheme
)
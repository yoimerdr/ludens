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
 */
data class Settings(
    val control: ControlSettings,
    val tool: ToolSettings,
    val positions: List<PositionableItem>
)
package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme

/**
 * Represents the complete application settings state in the presentation layer.
 *
 * This immutable data class aggregates all settings categories including control
 * configurations, tool settings, and UI element positions. It serves as the single
 * source of truth for the application's configuration state in the UI layer.
 *
 * @property control The [ControlSettingsState] containing on-screen control configurations.
 * @property tool The [ToolSettingsState] containing tool and utility configurations.
 * @property positions The list of [PositionableItemState] containing UI element positions.
 * @property theme The application's theme configuration.
 */
@Immutable
data class SettingsState(
    val control: ControlSettingsState = ControlSettingsState(),
    val tool: ToolSettingsState = ToolSettingsState(),
    val positions: List<PositionableItemState> = emptyList(),
    val theme: SystemTheme = SystemTheme.System,
) {
    /**
     * Companion object for [SettingsState] providing factory methods and default instances.
     */
    companion object
}
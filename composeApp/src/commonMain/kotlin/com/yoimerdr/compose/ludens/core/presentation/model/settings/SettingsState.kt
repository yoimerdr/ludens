package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme

/**
 * Represents the complete application settings state in the presentation layer.
 *
 * This immutable data class aggregates all settings categories including control
 * configurations, tool settings, and theme configuration. It serves as the single
 * source of truth for the application's configuration state in the UI layer.
 *
 * @property control The [ControlSettingsState] containing on-screen control configurations and positions.
 * @property tool The [ToolSettingsState] containing tool and utility configurations.
 * @property theme The application's theme configuration.
 */
@Immutable
data class SettingsState(
    val control: ControlSettingsState = ControlSettingsState(),
    val tool: ToolSettingsState = ToolSettingsState(),
    val theme: SystemTheme = SystemTheme.System,
) {
    /**
     * Companion object for [SettingsState] providing factory methods and default instances.
     */
    companion object
}
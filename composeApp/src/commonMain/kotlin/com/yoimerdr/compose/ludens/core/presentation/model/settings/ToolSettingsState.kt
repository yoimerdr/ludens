package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable

/**
 * Represents the tool and utility settings state in the presentation layer.
 *
 * This immutable data class holds configuration for various tool-related features
 * such as audio settings, performance monitoring displays, and rendering preferences.
 *
 * @property isMuted Whether audio is currently muted.
 * @property showFPS Whether the frames-per-second (FPS) counter should be displayed.
 * @property useWebGL Whether to use WebGL for rendering.
 */
@Immutable
data class ToolSettingsState(
    val isMuted: Boolean = false,
    val showFPS: Boolean = false,
    val useWebGL: Boolean = false,
)
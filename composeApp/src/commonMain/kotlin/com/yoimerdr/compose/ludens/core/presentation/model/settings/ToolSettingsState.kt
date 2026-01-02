package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable

/**
 * Represents the tool and utility settings state in the presentation layer.
 *
 * This immutable data class holds configuration for various tool-related features
 * such as audio settings and performance monitoring displays.
 *
 * @property isMuted Whether audio is currently muted.
 * @property showFPS Whether the frames-per-second (FPS) counter should be displayed.
 */
@Immutable
data class ToolSettingsState(
    val isMuted: Boolean = false,
    val showFPS: Boolean = false,
)
package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents tool-related settings for the application.
 *
 * @property isMuted Whether the audio is muted.
 * @property showFPS Whether the FPS (frames per second) counter is displayed.
 * @property useWebGL Whether to use WebGL for rendering.
 */
data class ToolSettings(
    val isMuted: Boolean,
    val showFPS: Boolean,
    val useWebGL: Boolean,
)
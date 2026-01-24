package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

/**
 * Base interface for tool settings events.
 */
sealed interface ToolSettingsEvent : SettingsEvent

/**
 * Updates the mute state of the application.
 *
 * @param enabled Whether audio is muted.
 */
data class UpdateAudioMuted(val enabled: Boolean) : ToolSettingsEvent

/**
 * Updates the FPS counter visibility.
 *
 * @param enabled Whether the FPS counter is visible.
 */
data class UpdateShowFps(val enabled: Boolean) : ToolSettingsEvent

/**
 * Updates the WebGL state of the application.
 *
 * @param enabled Whether WebGL is enabled.
 */
data class UpdateUseWebGL(val enabled: Boolean) : ToolSettingsEvent


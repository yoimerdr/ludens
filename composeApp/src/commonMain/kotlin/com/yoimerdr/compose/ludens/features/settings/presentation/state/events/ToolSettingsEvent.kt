package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

/**
 * Base interface for tool settings events.
 */
sealed interface ToolSettingsEvent : SettingsEvent {
    /**
     * Event indicating an update to tool settings.
     * */
    sealed interface UpdateSettings : ToolSettingsEvent,
        SettingsEvent.UpdateSettings
}

/**
 * Updates the mute state of the application.
 *
 * @param enabled Whether audio is muted.
 */
data class UpdateAudioMuted(
    val enabled: Boolean,
) : ToolSettingsEvent.UpdateSettings

/**
 * Updates the FPS counter visibility.
 *
 * @param enabled Whether the FPS counter is visible.
 */
data class UpdateShowFps(
    val enabled: Boolean,
) : ToolSettingsEvent.UpdateSettings

/**
 * Updates the WebGL state of the application.
 *
 * @param enabled Whether WebGL is enabled.
 */
data class UpdateUseWebGL(
    val enabled: Boolean,
) : ToolSettingsEvent.UpdateSettings


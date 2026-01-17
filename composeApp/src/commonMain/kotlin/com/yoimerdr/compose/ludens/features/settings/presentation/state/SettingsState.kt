package com.yoimerdr.compose.ludens.features.settings.presentation.state

import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState as SettingsStateModel

/**
 * Available sections in the settings screen.
 */
enum class SettingsSection {
    /** Control settings section. */
    Controls,

    /** Tools settings section. */
    Tools,

    /** System settings section. */
    System,

    /** About section. */
    About,
}

/**
 * User confirmation requests in the settings screen.
 */
sealed interface SettingsRequest {
    /** Request for mute. */
    data class RequestMute(
        val value: Boolean,
    ) : SettingsRequest
}

/**
 * Behavioral states of the settings screen.
 */
sealed interface SettingsMode {
    /** Initial loading state. */
    object Initializing : SettingsMode

    /** Normal idle state. */
    object Idle : SettingsMode

    /** Control movement mode is active. */
    object MovableControls : SettingsMode

    /** Pending confirmation mode with pending request. */
    data class PendingConfirmation(
        val request: SettingsRequest,
    ) : SettingsMode
}

/**
 * UI state for the settings screen.
 *
 * @param settings The current settings model.
 * @param section The currently selected section.
 * @param mode The current behavioral state.
 */
data class SettingsState(
    val settings: SettingsStateModel = SettingsStateModel.default,
    val section: SettingsSection = SettingsSection.Controls,
    val mode: SettingsMode = SettingsMode.Initializing,
)


package com.yoimerdr.compose.ludens.features.settings.presentation.state.requests

import com.yoimerdr.compose.ludens.ui.components.interaction.InteractionRequest

/**
 * User confirmation requests in the settings screen.
 */
sealed interface SettingsRequest {
    sealed interface Interaction : SettingsRequest, InteractionRequest

    sealed interface RequiredRestart : SettingsRequest
}


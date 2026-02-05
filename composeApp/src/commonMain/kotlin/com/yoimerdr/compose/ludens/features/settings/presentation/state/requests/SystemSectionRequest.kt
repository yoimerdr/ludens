package com.yoimerdr.compose.ludens.features.settings.presentation.state.requests

sealed interface SystemSectionRequest : SettingsRequest


/**
 * Request to restore restarting settings
 * */
data object RequestRestartingRestore : SystemSectionRequest,
    SettingsRequest.RequiredRestart, SettingsRequest.Interaction

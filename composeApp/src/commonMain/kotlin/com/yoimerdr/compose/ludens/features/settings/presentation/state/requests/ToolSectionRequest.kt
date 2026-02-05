package com.yoimerdr.compose.ludens.features.settings.presentation.state.requests

sealed interface ToolSectionRequest : SettingsRequest

/** Request for mute confirmation. */
data class RequestMute(
    val value: Boolean,
) : ToolSectionRequest, SettingsRequest.Interaction

/** Request for WebGL usage confirmation. */
data class RequestWebGL(
    val value: Boolean,
) : ToolSectionRequest, SettingsRequest.Interaction
package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.ui.components.interaction.InteractionRequest

/**
 * Requests that can be made in the home screen.
 */
sealed interface HomeRequest

/**
 * Requests that require restarting the application or component.
 */
sealed interface RestartingRequest : HomeRequest, InteractionRequest

/**
 * Request to mute or unmute audio.
 *
 * @property enabled Whether audio should be muted (true) or unmuted (false).
 */
data class RequestMute(val enabled: Boolean) : RestartingRequest

/**
 * Request to enable or disable WebGL.
 *
 * @property enabled Whether WebGL should be enabled (true) or disabled (false).
 */
data class RequestWebGL(val enabled: Boolean) : RestartingRequest
package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey

sealed interface ControlSettingsEvent : SettingsEvent

/**
 * Updates the enabled state of all controls (that can be disabled).
 *
 * @param enabled Whether controls are enabled.
 */
data class UpdateControlsEnabled(val enabled: Boolean) :
    ControlSettingsEvent

/**
 * Updates the alpha transparency of all controls.
 *
 * @param alpha The alpha value.
 */
data class UpdateControlsAlpha(val alpha: Float) :
    ControlSettingsEvent

/**
 * Updates the enabled state of a specific control.
 *
 * @param index The control index.
 * @param enabled Whether the control is enabled.
 */
data class UpdateControlEnabled(val index: Int, val enabled: Boolean) :
    ControlSettingsEvent

/**
 * Updates the alpha transparency of a specific control.
 *
 * @param index The control index.
 * @param alpha The alpha value.
 */
data class UpdateControlAlpha(val index: Int, val alpha: Float) :
    ControlSettingsEvent

/**
 * Updates the key binding for a specific control.
 *
 * @param index The control index.
 * @param key The input key to bind.
 */
class UpdateControlKey(val index: Int, val key: InputKey) :
    ControlSettingsEvent
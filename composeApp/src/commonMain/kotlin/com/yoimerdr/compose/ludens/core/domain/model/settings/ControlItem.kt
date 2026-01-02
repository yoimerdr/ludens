package com.yoimerdr.compose.ludens.core.domain.model.settings

import com.yoimerdr.compose.ludens.core.domain.value.Alpha

/**
 * Represents a single control item configuration.
 *
 * @property type The type of control (Joystick, KeyA, KeyB, etc.).
 * @property enabled Whether this control item is enabled.
 * @property alpha The alpha/transparency value for this control.
 * @property code Optional key code associated with this control.
 */
data class ControlItem(
    val type: ItemType,
    val enabled: Boolean,
    val alpha: Alpha,
    val code: Int? = null,
)
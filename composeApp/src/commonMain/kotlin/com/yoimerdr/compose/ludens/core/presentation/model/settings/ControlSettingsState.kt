package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable

/**
 * Represents the state of on-screen controls in the presentation layer.
 *
 * This immutable data class holds the configuration for on-screen game controls,
 * including their visibility, opacity, and the list of individual control items.
 *
 * @property enabled Whether on-screen controls are currently enabled and visible.
 * @property alpha The overall opacity level for all controls (0.0 to 1.0).
 * @property items The list of individual [ControlItemState] elements that make up the controls.
 */
@Immutable
data class ControlSettingsState(
    val enabled: Boolean = false,
    val alpha: Float = 1f,
    val items: List<ControlItemState> = emptyList(),
)
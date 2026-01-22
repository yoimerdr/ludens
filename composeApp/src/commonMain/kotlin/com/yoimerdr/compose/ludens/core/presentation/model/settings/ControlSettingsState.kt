package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the state of on-screen controls in the presentation layer.
 *
 * This immutable data class holds the configuration for on-screen game controls,
 * including their visibility, opacity, the list of individual control items, and their positions.
 *
 * @property enabled Whether on-screen controls are currently enabled and visible.
 * @property alpha The overall opacity level for all controls (0.0 to 1.0).
 * @property items The list of individual [ControlItemState] elements that make up the controls.
 * @property positions The list of [PositionableItemState] containing UI element positions.
 */
@Immutable
data class ControlSettingsState(
    val enabled: Boolean = false,
    val alpha: Float = 1f,
    val items: ImmutableList<ControlItemState> = persistentListOf(),
    val positions: ImmutableList<PositionableItemState> = persistentListOf(),
)
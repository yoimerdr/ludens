package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

import androidx.compose.ui.geometry.Rect
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType

sealed interface ControlPositionsSettingsEvent : SettingsEvent

/**
 * Updates the position of a control.
 *
 * @param index The control index.
 * @param x The x coordinate.
 * @param y The y coordinate.
 */
data class UpdateControlPosition(
    val index: Int,
    val x: Float,
    val y: Float,
) : ControlPositionsSettingsEvent

/**
 * Swaps the positions of two controls within specified bounds.
 *
 * @property indices A pair of indices representing the two controls to swap.
 * @property bounds The [Rect] boundary within which the controls should remain after swapping.
 */
data class SwapControlPositions(
    val indices: Pair<Int, Int>,
    val bounds: Rect,
) : ControlPositionsSettingsEvent

/**
 * Resets the positions of specific controls to their default values.
 *
 * @property items The set of [PositionableType] to reset to default positions.
 * If constructed with the no-arg constructor, all positionable controls are reset.
 */
data class RestoreDefaultControlPositions(
    val items: Set<PositionableType>,
) : ControlPositionsSettingsEvent {
    /**
     * Constructs a [RestoreDefaultControlPositions] event that resets all positionable controls.
     */
    constructor() : this(PositionableType.entries.toSet())
}
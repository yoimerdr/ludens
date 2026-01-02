package com.yoimerdr.compose.ludens.core.presentation.model.settings

import androidx.compose.runtime.Immutable
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType

/**
 * Represents the position state of a UI element in the presentation layer.
 *
 * This immutable data class stores the 2D position coordinates of positionable
 * UI elements such as controls.
 *
 * @property type The [PositionableType] that identifies this positionable element.
 * @property x The horizontal position coordinate.
 * @property y The vertical position coordinate.
 */
@Immutable
data class PositionableItemState(
    val type: PositionableType,
    val x: Float,
    val y: Float,
)

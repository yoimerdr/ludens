package com.yoimerdr.compose.ludens.core.domain.model.settings

/**
 * Represents a positionable UI element with its coordinates.
 *
 * @property type The type of positionable element.
 * @property x The x-coordinate position.
 * @property y The y-coordinate position.
 */
data class PositionableItem(
    val type: PositionableType,
    val x: Float,
    val y: Float,
)
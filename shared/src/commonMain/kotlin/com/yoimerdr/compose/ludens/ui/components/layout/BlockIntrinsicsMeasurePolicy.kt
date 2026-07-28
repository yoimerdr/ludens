package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints

/**
 * A custom [MeasurePolicy] that blocks intrinsic size calculations.
 *
 * This measure policy measures the content normally but returns zero for all
 * intrinsic measurements. Useful when you want to prevent the parent layout from
 * querying intrinsic sizes, which can cause sizing issues in certain layouts.
 *
 * The policy measures the first measurable with the given constraints and places
 * it at position (0, 0), returning a layout with dimensions matching the content.
 */
val BlockIntrinsicsMeasurePolicy = object : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        val placeable = measurables.firstOrNull()?.measure(constraints)
        return layout(placeable?.width ?: 0, placeable?.height ?: 0) {
            placeable?.placeRelative(0, 0)
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ) = 0

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ) = 0

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ) = 0

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ) = 0
}
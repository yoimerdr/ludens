package com.yoimerdr.compose.ludens.ui.extensions.layout

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin

/**
 * Extension properties for [TransformOrigin.Companion] providing all possible alignment positions
 * for transformations (scale, rotation, etc.).
 *
 * ## Understanding TransformOrigin
 *
 * TransformOrigin defines the **pivot point** for transformations using normalized coordinates:
 * - X axis: 0f (start/left) to 1f (end/right)
 * - Y axis: 0f (top) to 1f (bottom)
 *
 * These extensions provide convenient access to the 9 common alignment positions:
 * ```
 * TopStart    TopCenter    TopEnd
 * CenterStart Center       CenterEnd
 * BottomStart BottomCenter BottomEnd
 * ```
 *
 * ## Important: TransformOrigin vs Alignment
 *
 * **TransformOrigin** and **Alignment** work together but serve different purposes:
 *
 * - **Alignment** (in Box, Column, etc.): Determines where the element is **positioned in the layout**
 * - **TransformOrigin**: Determines the **pivot point for transformations** (scale, rotation, etc.)
 *
 * ### Common Issue: Element moves to center when scaling
 *
 * If you use `Alignment.BottomCenter` but the element moves toward the center when scaled,
 * it's because you're using the default `TransformOrigin.Center` (0.5f, 0.5f).
 *
 * **Solution:** Match the TransformOrigin to your desired Alignment:
 * ```kotlin
 * Box(
 *     modifier = Modifier.fillMaxSize(),
 *     contentAlignment = Alignment.BottomCenter  // Position in layout
 * ) {
 *     Box(
 *         modifier = Modifier
 *             .size(100.dp)
 *             .graphicsLayer {
 *                 transformOrigin = TransformOrigin.BottomCenter  // Pivot point for scaling
 *                 scaleX = 2f
 *                 scaleY = 2f
 *             }
 *     )
 * }
 * ```
 *
 * ### Visual Examples:
 *
 * **BottomCenter TransformOrigin:**
 * - Element scales upward from its bottom edge
 * - Stays anchored at the bottom position set by Alignment
 * - Perfect for bottom-anchored animations (like bottom sheets, tooltips)
 *
 * **TopCenter TransformOrigin:**
 * - Element scales downward from its top edge
 * - Stays anchored at the top position set by Alignment
 * - Perfect for dropdown menus, top notifications
 *
 * **Center TransformOrigin (default):**
 * - Element scales equally in all directions from its center
 * - Visual center stays at the position set by Alignment
 * - Perfect for zoom effects, dialog animations
 */

// Top row

@Suppress("ObjectPropertyName")
private var _TopStart: TransformOrigin? = null

/**
 * Transform origin at the top-start corner (top-left in LTR, top-right in RTL).
 * Coordinates: (0f, 0f)
 *
 * When scaling from this origin, the element expands towards the bottom-end direction.
 */
val TransformOrigin.Companion.TopStart: TransformOrigin
    @Stable
    get() {
        if (_TopStart == null) {
            _TopStart = TransformOrigin(0f, 0f)
        }
        return _TopStart!!
    }

@Suppress("ObjectPropertyName")
private var _TopCenter: TransformOrigin? = null

/**
 * Transform origin at the top-center.
 * Coordinates: (0.5f, 0f)
 *
 * When scaling from this origin, the element expands horizontally in both directions
 * and downwards vertically.
 */
val TransformOrigin.Companion.TopCenter: TransformOrigin
    @Stable
    get() {
        if (_TopCenter == null) {
            _TopCenter = TransformOrigin(0.5f, 0f)
        }
        return _TopCenter!!
    }

@Suppress("ObjectPropertyName")
private var _TopEnd: TransformOrigin? = null

/**
 * Transform origin at the top-end corner (top-right in LTR, top-left in RTL).
 * Coordinates: (1f, 0f)
 *
 * When scaling from this origin, the element expands towards the bottom-start direction.
 */
val TransformOrigin.Companion.TopEnd: TransformOrigin
    @Stable
    get() {
        if (_TopEnd == null) {
            _TopEnd = TransformOrigin(1f, 0f)
        }
        return _TopEnd!!
    }

// Center row

@Suppress("ObjectPropertyName")
private var _CenterStart: TransformOrigin? = null

/**
 * Transform origin at the center-start (middle-left in LTR, middle-right in RTL).
 * Coordinates: (0f, 0.5f)
 *
 * When scaling from this origin, the element expands vertically in both directions
 * and towards the end horizontally.
 */
val TransformOrigin.Companion.CenterStart: TransformOrigin
    @Stable
    get() {
        if (_CenterStart == null) {
            _CenterStart = TransformOrigin(0f, 0.5f)
        }
        return _CenterStart!!
    }

// Note: Center (0.5f, 0.5f) already exists in TransformOrigin.Companion

@Suppress("ObjectPropertyName")
private var _CenterEnd: TransformOrigin? = null

/**
 * Transform origin at the center-end (middle-right in LTR, middle-left in RTL).
 * Coordinates: (1f, 0.5f)
 *
 * When scaling from this origin, the element expands vertically in both directions
 * and towards the start horizontally.
 */
val TransformOrigin.Companion.CenterEnd: TransformOrigin
    @Stable
    get() {
        if (_CenterEnd == null) {
            _CenterEnd = TransformOrigin(1f, 0.5f)
        }
        return _CenterEnd!!
    }

// Bottom row

@Suppress("ObjectPropertyName")
private var _BottomStart: TransformOrigin? = null

/**
 * Transform origin at the bottom-start corner (bottom-left in LTR, bottom-right in RTL).
 * Coordinates: (0f, 1f)
 *
 * When scaling from this origin, the element expands towards the top-end direction.
 */
val TransformOrigin.Companion.BottomStart: TransformOrigin
    @Stable
    get() {
        if (_BottomStart == null) {
            _BottomStart = TransformOrigin(0f, 1f)
        }
        return _BottomStart!!
    }

@Suppress("ObjectPropertyName")
private var _BottomCenter: TransformOrigin? = null

/**
 * Transform origin at the bottom-center.
 * Coordinates: (0.5f, 1f)
 *
 * When scaling from this origin, the element expands horizontally in both directions
 * and upwards vertically. This is commonly used for bottom-anchored animations.
 */
val TransformOrigin.Companion.BottomCenter: TransformOrigin
    @Stable
    get() {
        if (_BottomCenter == null) {
            _BottomCenter = TransformOrigin(0.5f, 1f)
        }
        return _BottomCenter!!
    }

@Suppress("ObjectPropertyName")
private var _BottomEnd: TransformOrigin? = null

/**
 * Transform origin at the bottom-end corner (bottom-right in LTR, bottom-left in RTL).
 * Coordinates: (1f, 1f)
 *
 * When scaling from this origin, the element expands towards the top-start direction.
 */
val TransformOrigin.Companion.BottomEnd: TransformOrigin
    @Stable
    get() {
        if (_BottomEnd == null) {
            _BottomEnd = TransformOrigin(1f, 1f)
        }
        return _BottomEnd!!
    }


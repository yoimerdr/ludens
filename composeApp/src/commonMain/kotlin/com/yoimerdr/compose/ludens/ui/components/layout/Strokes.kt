package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines a consistent border stroke scale for UI components.
 *
 * This interface provides a set of predefined stroke values that should be used
 * throughout the application to maintain visual consistency.
 */
interface Strokes {
    /**
     * Thin stroke value.
     *
     * Used for subtle dividers and fine borders.
     */
    val thin: Dp

    /**
     * Focus stroke value.
     *
     * Used for focus indicators and selected states.
     */
    val focus: Dp

    /**
     * Heavy stroke value.
     *
     * Used for prominent borders and emphasis.
     */
    val heavy: Dp
}

/**
 * Default [Strokes] implementation used throughout Ludens.
 */
@Immutable
object DefaultStrokes : Strokes {
    override val thin: Dp = 1.0f.dp
    override val focus: Dp = 2.0f.dp
    override val heavy: Dp = 3.0f.dp
}

package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines a consistent border radius scale for UI components.
 *
 * This interface provides a set of predefined radius values that should be used
 * throughout the application to maintain visual consistency.
 */
interface Radius {
    /**
     * Micro radius value.
     *
     * Used for compact elements and small buttons.
     */
    val micro: Dp

    /**
     * Standard radius value.
     *
     * Used for standard UI components and controls.
     */
    val standard: Dp

    /**
     * Comfortable radius value.
     *
     * Used for input fields and medium-sized components.
     */
    val comfortable: Dp

    /**
     * Large radius value.
     *
     * Used for cards and large containers.
     */
    val large: Dp

    /**
     * Pill radius value.
     *
     * Used for fully rounded elements like pills and round buttons.
     */
    val pill: Dp
}

/**
 * Default [Radius] implementation used throughout Ludens.
 */
@Immutable
object DefaultRadius : Radius {
    override val micro: Dp = 5.0f.dp
    override val standard: Dp = 8.0f.dp
    override val comfortable: Dp = 11.0f.dp
    override val large: Dp = 12.0f.dp
    override val pill: Dp = 980.0f.dp
}

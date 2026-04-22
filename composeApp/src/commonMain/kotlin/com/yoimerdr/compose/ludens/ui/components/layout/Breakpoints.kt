package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines a consistent breakpoint scale for responsive mobile UI layout.
 *
 * This interface provides a set of predefined width values that should be used
 * throughout the application to maintain responsive design consistency.
 */
interface Breakpoints {
    /**
     * Extra small breakpoint value.
     *
     */
    val extraSmall: Dp

    /**
     * Small breakpoint value.
     */
    val small: Dp

    /**
     * Medium breakpoint value.
     */
    val medium: Dp

    /**
     * Large breakpoint value.
     */
    val large: Dp

    /**
     * Extra large breakpoint value.
     */
    val extraLarge: Dp

    /**
     * XX large breakpoint value.
     */
    val xxLarge: Dp

    /**
     * Wide breakpoint value.
     */
    val wide: Dp
}

/**
 * Default [Breakpoints] implementation used throughout Ludens.
 */
@Immutable
object DefaultBreakpoints : Breakpoints {
    override val extraSmall: Dp = 360.0f.dp
    override val small: Dp = 480.0f.dp
    override val medium: Dp = 640.0f.dp
    override val large: Dp = 834.0f.dp
    override val extraLarge: Dp = 1024.0f.dp
    override val xxLarge: Dp = 1070.0f.dp
    override val wide: Dp = 1440.0f.dp
}

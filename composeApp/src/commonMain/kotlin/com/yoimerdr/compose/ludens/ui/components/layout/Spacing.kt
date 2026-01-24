package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines a consistent spacing scale for UI layout.
 *
 * This interface provides a set of predefined spacing values that should be used
 * throughout the application to maintain visual consistency.
 */
interface Spacing {
    /**
     * Extra small spacing value.
     *
     * Used for minimal spacing between closely related elements.
     */
    val extraSmall: Dp

    /**
     * Small spacing value.
     *
     * Used for spacing between related elements within a component.
     */
    val small: Dp

    /**
     * Medium spacing value.
     *
     * Used for standard spacing between components or sections.
     */
    val medium: Dp

    /**
     * Large spacing value.
     *
     * Used for spacing between major sections or groups.
     */
    val large: Dp

    /**
     * Extra large spacing value.
     *
     * Used for maximum spacing between distinct content areas.
     */
    val extraLarge: Dp
}

/**
 * Default implementation of [Spacing] following Material Design 3 spacing scale.
 *
 * Provides the standard spacing values that should be used across the application
 * to ensure consistent visual rhythm and alignment.
 */
object DefaultSpacing : Spacing {
    override val extraSmall: Dp = 4.dp
    override val small: Dp = 8.dp
    override val medium: Dp = 16.dp
    override val large: Dp = 24.dp
    override val extraLarge: Dp = 32.dp
}
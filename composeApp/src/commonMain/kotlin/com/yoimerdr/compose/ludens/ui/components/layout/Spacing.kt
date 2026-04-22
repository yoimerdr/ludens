package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.runtime.Immutable
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
     * Hairline spacing value.
     *
     * Used for the most compact separations and focus treatments.
     */
    val hairline: Dp

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
     * Comfortable spacing value.
     *
     * Used for compact padding in inputs and medium-density controls.
     */
    val comfortable: Dp

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
 * Default [Spacing] implementation used throughout Ludens.
 */
@Immutable
object DefaultSpacing : Spacing {
    override val hairline: Dp = 2.0f.dp
    override val extraSmall: Dp = 4.0f.dp
    override val small: Dp = 8.0f.dp
    override val comfortable: Dp = 11.0f.dp
    override val medium: Dp = 14.0f.dp
    override val large: Dp = 17.0f.dp
    override val extraLarge: Dp = 24.0f.dp
}

package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.ui.components.provider.LocalBreakpoints

/**
 * Adaptive layout composable that renders content based on [Breakpoints].
 *
 * This function automatically selects the appropriate layout based on the current screen width.
 * If a layout for a specific breakpoint is not provided,
 * it falls back to the next smaller breakpoint layout.
 *
 * **Fallback behavior:**
 * - Compact (0-[Breakpoints.medium]): Uses `compact` layout
 * - Medium ([Breakpoints.medium]-[Breakpoints.extraLarge]): Uses `medium` layout, falls back to `compact` if not provided
 * - Expanded ([Breakpoints.extraLarge]+): Uses `expanded` layout, falls back to `medium`, then `compact` if not provided
 *
 * @param modifier Modifier to be applied to the layout.
 * @param compact The composable content to display for compact screens (phones in portrait).
 * @param medium The composable content to display for medium screens (tablets in portrait). Falls back to `compact` if null.
 * @param expanded The composable content to display for expanded screens (tablets in landscape, desktops). Falls back to `medium` or `compact` if null.
 *
 * @see Breakpoints
 */
@Composable
fun ResponsiveBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    compact: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
    medium: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
    expanded: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        val breakpoints = LocalBreakpoints.current

        when {
            maxWidth < breakpoints.medium -> {
                compact?.invoke(this)
            }

            maxWidth < breakpoints.extraLarge -> {
                medium?.invoke(this) ?: compact?.invoke(this)
            }

            else -> {
                expanded?.invoke(this) ?: medium?.invoke(this) ?: compact?.invoke(this)
            }
        }
    }
}

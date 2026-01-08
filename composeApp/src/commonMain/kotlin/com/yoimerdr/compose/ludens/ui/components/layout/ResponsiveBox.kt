package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Material Design 3 breakpoint definitions for responsive layouts.
 *
 * These breakpoints follow the Material Design 3 guidelines for adaptive layouts,
 * defining three window size classes based on screen width:
 * - **Compact**: 0dp - 599dp (phones in portrait mode)
 * - **Medium**: 600dp - 839dp (tablets in portrait, foldables)
 * - **Expanded**: 840dp+ (tablets in landscape, desktops)
 *
 * @see <a href="https://m3.material.io/foundations/layout/applying-layout/window-size-classes">Material Design 3 Window Size Classes</a>
 */
object Breakpoints {
    /**
     * Compact window size class (0dp+).
     *
     * Applies to small screens such as phones in portrait mode.
     */
    val Compact = 0.dp

    /**
     * Medium window size class (600dp+).
     *
     * Applies to medium screens such as tablets in portrait mode or foldable devices.
     */
    val Medium = 600.dp

    /**
     * Expanded window size class (840dp+).
     *
     * Applies to large screens such as tablets in landscape mode or desktop displays.
     */
    val Expanded = 840.dp
}

/**
 * Adaptive layout composable that renders content based on [Breakpoints].
 *
 * This function automatically selects the appropriate layout based on the current screen width.
 * If a layout for a specific breakpoint is not provided,
 * it falls back to the next smaller breakpoint layout.
 *
 * **Fallback behavior:**
 * - Compact (0-599dp): Uses `compact` layout
 * - Medium (600-839dp): Uses `medium` layout, falls back to `compact` if not provided
 * - Expanded (840dp+): Uses `expanded` layout, falls back to `medium`, then `compact` if not provided
 *
 * @param compact The composable content to display for compact screens (phones in portrait).
 * @param medium The composable content to display for medium screens (tablets in portrait). Falls back to `compact` if null.
 * @param expanded The composable content to display for expanded screens (tablets in landscape, desktops). Falls back to `medium` or `compact` if null.
 *
 * @see Breakpoints
 */
@Composable
fun ResponsiveBox(
    compact: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
    medium: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
    expanded: (@Composable BoxWithConstraintsScope.() -> Unit)? = null,
) {
    BoxWithConstraints {
        when {
            maxWidth < Breakpoints.Medium -> {
                compact?.invoke(this)
            }

            maxWidth < Breakpoints.Expanded -> {
                medium?.invoke(this) ?: compact?.invoke(this)
            }

            else -> {
                expanded?.invoke(this) ?: medium?.invoke(this) ?: compact?.invoke(this)
            }
        }
    }
}
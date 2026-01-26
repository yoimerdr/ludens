package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.yoimerdr.compose.ludens.ui.state.Edge
import com.yoimerdr.compose.ludens.ui.state.layout.FloatingState
import com.yoimerdr.compose.ludens.ui.state.layout.FloatingStrategy
import com.yoimerdr.compose.ludens.ui.state.layout.rememberFloatingState
import com.yoimerdr.compose.ludens.ui.state.layout.setOpenEdge


/**
 * Creates a slide-in transition from this edge.
 *
 * The content slides in from the specified edge towards the center.
 *
 * @param animationSpec The animation specification for the transition.
 */
fun Edge.asSlideInTransition(
    animationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold,
    ),
): EnterTransition {
    return when (this) {
        Edge.Right -> slideInHorizontally(animationSpec) { -it / 2 }
        Edge.Left -> slideInHorizontally(animationSpec) { it / 2 }
        Edge.Bottom -> slideInVertically(animationSpec) { -it / 2 }
        Edge.Top -> slideInVertically(animationSpec) { it / 2 }
    }
}

/**
 * Creates a slide-out transition towards this edge.
 * The content slides out from the center towards the specified edge.
 *
 * @param animationSpec The animation specification for the transition.
 */
fun Edge.asSlideOutTransition(
    animationSpec: FiniteAnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntOffset.VisibilityThreshold,
    ),
): ExitTransition {
    return when (this) {
        Edge.Right -> slideOutHorizontally(animationSpec) { -it / 2 }
        Edge.Left -> slideOutHorizontally(animationSpec) { it / 2 }
        Edge.Bottom -> slideOutVertically(animationSpec) { -it / 2 }
        Edge.Top -> slideOutVertically(animationSpec) { it / 2 }
    }
}

/**
 * Creates and remembers an enter transition combining fade-in and slide-in from the specified edge.
 *
 * @param edge The edge from which the content will slide in.
 * @return A combined [EnterTransition] with fade and slide effects.
 */
@Composable
fun rememberEnterTransition(
    edge: Edge,
): EnterTransition {
    return remember(edge) {
        fadeIn() + edge.asSlideInTransition()
    }
}

/**
 * Creates and remembers an enter transition for a floating state.
 *
 * @param state The floating state containing the edge information.
 * @return A combined [EnterTransition] with fade and slide effects from the state's edge.
 */
@Composable
fun rememberEnterTransition(
    state: FloatingState,
): EnterTransition = rememberEnterTransition(state.edge)

/**
 * Creates and remembers an exit transition combining fade-out and slide-out towards the specified edge.
 *
 * @param edge The edge towards which the content will slide out.
 * @return A combined [ExitTransition] with fade and slide effects.
 */
@Composable
fun rememberExitTransition(
    edge: Edge,
): ExitTransition {
    return remember(edge) {
        fadeOut() + edge.asSlideOutTransition()
    }
}

/**
 * Creates and remembers an exit transition for a floating state.
 *
 * @param state The floating state containing the edge information.
 * @return A combined [ExitTransition] with fade and slide effects towards the state's edge.
 */
@Composable
fun rememberExitTransition(
    state: FloatingState,
): ExitTransition = rememberExitTransition(state.edge)

/**
 * Modifier that observes the position of a composable and reports its center position
 * relative to its parent container.
 *
 * This is used by the floating system to determine available space
 * in all directions for optimal placement.
 *
 * @param onPositioned Callback invoked when the composable is positioned, providing:
 *   * `center`: The center point of the composable in parent coordinates
 *   * `size`: The size of the parent container
 */
fun Modifier.onFloatingPositioned(
    onPositioned: (center: Offset, size: Size) -> Unit,
): Modifier =
    onGloballyPositioned { coordinates ->
        val parent = coordinates.parentLayoutCoordinates ?: coordinates.findRootCoordinates()

        val parentSize = parent.size

        val position = parent.localPositionOf(coordinates)
        val size = coordinates.size

        val centerX = position.x + size.width / 2f
        val centerY = position.y + size.height / 2f

        onPositioned(
            Offset(centerX, centerY),
            parentSize.toSize()
        )
    }

/**
 * Modifier that automatically determines the optimal edge for a floating to open from
 * based on the specified strategy and available space.
 *
 * This modifier calculates the opener's center position and parent container size,
 * then uses the strategy to determine which edge provides the best placement.
 *
 * @param strategy The strategy for determining the opening edge.
 * @param state The floating state to update with the calculated edge.
 *
 * @see FloatingStrategy
 * @see onFloatingPositioned
 * @see setOpenEdge
 */
fun Modifier.floating(
    strategy: FloatingStrategy,
    state: FloatingState,
): Modifier = onFloatingPositioned { center, size ->
    state.setOpenEdge(
        strategy = strategy,
        center = center,
        size = size
    )
}

/**
 * A floating component that displays a opener and content that appears to an edge.
 *
 * This intelligently determines which edge to open from based on the specified strategy
 * and available space. It supports smooth enter/exit animations and customizable content.
 *
 * @param state The state managing the open/closed status and edge.
 * @param modifier The modifier to apply to the container.
 * @param strategy The strategy for determining which edge opens tp.
 * @param gap The spacing between the opener and content when open.
 * @param enterTransition Factory function for creating the enter animation.
 * @param exitTransition Factory function for creating the exit animation.
 * @param opener The opener composable that triggers the content.
 * @param content The content composable displayed when open.
 *
 * @see FloatingState
 * @see FloatingStrategy
 * @see rememberFloatingState
 * @see rememberEnterTransition
 * @see rememberExitTransition
 */
@Composable
fun Floating(
    state: FloatingState = rememberFloatingState(),
    modifier: Modifier = Modifier,
    strategy: FloatingStrategy = FloatingStrategy.Auto,
    gap: Dp = 0f.dp,
    enterTransition: (@Composable (FloatingState) -> EnterTransition) = ::rememberEnterTransition,
    exitTransition: (@Composable (FloatingState) -> ExitTransition) = ::rememberExitTransition,
    opener: @Composable (FloatingState) -> Unit,
    content: @Composable (FloatingState) -> Unit,
) {
    val enterTransition = enterTransition(state)
    val exitTransition = exitTransition(state)

    FloatingLayout(
        modifier = modifier.floating(
            strategy = strategy,
            state = state
        ),
        state = state,
        gap = gap,
        opener = {
            Box(
                modifier = Modifier.zIndex(1f)
            ) {
                opener(state)
            }
        }
    ) {
        AnimatedVisibility(
            visible = state.isOpen, enter = enterTransition, exit = exitTransition
        ) {
            content(state)
        }
    }
}

/**
 * The floating layout component that positions the opener and content.
 *
 * @param state The floating state containing the edge information.
 * @param modifier The modifier to apply to the layout.
 * @param gap The spacing between the opener and content.
 * @param opener The opener composable.
 * @param content The content composable.
 */
@Composable
fun FloatingLayout(
    state: FloatingState,
    modifier: Modifier = Modifier,
    gap: Dp = 0f.dp,
    opener: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Layout(
        contents = listOf(opener, content), modifier = modifier
    ) { (targetMeasurables, contentMeasurables), constraints ->

        val target = targetMeasurables.first().measure(constraints)
        val width = target.width
        val height = target.height

        val content =
            contentMeasurables.firstOrNull()?.measure(constraints.copy(minWidth = 0, minHeight = 0))
        val centerWidth = content?.width ?: 0
        val centerHeight = content?.height ?: 0
        val gapPx = gap.roundToPx()

        layout(width, height) {
            target.placeRelative(0, 0)

            if (content != null) {
                val (x, y) = when (state.edge) {
                    Edge.Right -> (width + gapPx) to ((height - centerHeight) / 2)
                    Edge.Left -> (-centerWidth - gapPx) to ((height - centerHeight) / 2)
                    Edge.Bottom -> ((width - centerWidth) / 2) to (height + gapPx)
                    Edge.Top -> ((width - centerWidth) / 2) to (-centerHeight - gapPx)
                }
                content.placeRelative(x, y)
            }
        }
    }
}

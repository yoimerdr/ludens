package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.IntOffset
import com.yoimerdr.compose.ludens.ui.state.layout.DockMode
import com.yoimerdr.compose.ludens.ui.state.layout.DockState
import com.yoimerdr.compose.ludens.ui.state.layout.DockStrategy
import com.yoimerdr.compose.ludens.ui.state.layout.rememberDockState
import com.yoimerdr.compose.ludens.ui.state.layout.setOpenBounds
import kotlinx.coroutines.delay

/**
 * Modifier that automatically determines the optimal edge for an element to dock to
 * based on the specified strategy and its position within the parent.
 *
 * This modifier calculates the element's bounds and parent container size,
 * then uses the strategy to determine which edge provides the best docking placement.
 *
 * @param state The dock state to update with the calculated edge and bounds.
 * @param strategy The strategy for determining the docking edge.
 *
 * @see DockStrategy
 * @see setOpenBounds
 */
fun Modifier.dockable(
    state: DockState,
    strategy: DockStrategy = DockStrategy.Auto,
): Modifier {
    return onPlaced { coordinates ->
        val parent =
            coordinates.parentLayoutCoordinates ?: coordinates.findRootCoordinates()

        val bounds = parent.localBoundingBoxOf(coordinates)
        state.setOpenBounds(
            container = parent.boundsInParent(),
            bounds = bounds,
            strategy = strategy
        )
    }
}

/**
 * A layout that can dock its content to the edges of the screen.
 *
 * The content will automatically dock after the specified timeout when in docked mode.
 * The docking animation and position can be customized through the state and parameters.
 *
 * @param modifier The modifier to apply to this layout.
 * @param state The dock state controlling the docking behavior.
 * @param animationSpec The animation specification for the offset movement.
 * @param onDocked A callback that is invoked when the element docks.
 * @param strategy The strategy for determining which edge to dock to.
 * @param content The content to be docked, receiving the dock state as a parameter.
 * @param timeout The timeout in milliseconds before the element docks automatically.
 *
 * @see [dockable]
 */
@Composable
fun DockLayout(
    modifier: Modifier = Modifier,
    state: DockState = rememberDockState(),
    timeout: Long = 3000L,
    animationSpec: AnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessMediumLow,
        dampingRatio = Spring.DampingRatioLowBouncy
    ),
    strategy: DockStrategy = DockStrategy.Auto,
    onDocked: ((DockState) -> Unit)? = null,
    content: @Composable (DockState) -> Unit,
) {
    LaunchedEffect(state.mode) {
        delay(timeout)
        if (state.mode == DockMode.Idle) {
            state.dock()
            onDocked?.invoke(state)
        }
    }

    val offset by animateIntOffsetAsState(
        targetValue = state.offset,
        animationSpec = animationSpec,
        label = "Move",
    )

    Box(
        modifier = modifier
            .dockable(
                state = state,
                strategy = strategy
            )
            .offset { offset }
    ) {
        content(state)
    }
}
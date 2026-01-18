package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Circle
import com.yoimerdr.compose.ludens.ui.icons.outlined.Dismiss
import com.yoimerdr.compose.ludens.ui.icons.outlined.HandleBottom
import com.yoimerdr.compose.ludens.ui.icons.outlined.HandleLeft
import com.yoimerdr.compose.ludens.ui.icons.outlined.HandleRight
import com.yoimerdr.compose.ludens.ui.icons.outlined.HandleTop
import com.yoimerdr.compose.ludens.ui.state.Edge
import com.yoimerdr.compose.ludens.ui.state.layout.DockState
import com.yoimerdr.compose.ludens.ui.state.layout.DockStrategy
import com.yoimerdr.compose.ludens.ui.state.layout.FloatingState
import com.yoimerdr.compose.ludens.ui.state.layout.isDocked
import com.yoimerdr.compose.ludens.ui.state.layout.isStatic
import com.yoimerdr.compose.ludens.ui.state.layout.rememberDockState
import com.yoimerdr.compose.ludens.ui.state.layout.rememberFloatingState
import com.yoimerdr.compose.ludens.ui.state.layout.toFloatingStrategy
import com.yoimerdr.compose.ludens.ui.state.reverse

/**
 * A button that displays the appropriate handle icon for the current dock edge.
 *
 * This button puts the dockable element into standby mode when clicked.
 *
 * @param state The dock state controlling the docking behavior.
 * @param modifier The modifier to apply to the button.
 * @param tint The color tint for the handle icon.
 *
 * @see DockState
 * @see DockState.standby
 */
@Composable
fun FloatingDockHandleButton(
    state: DockState,
    modifier: Modifier = Modifier,
    tint: Color = Color.DarkGray,
) {
    Box(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = state::standby
            ),
    ) {
        Icon(
            imageVector = when (state.edge) {
                Edge.Top -> LudensIcons.Outlined.HandleTop
                Edge.Bottom -> LudensIcons.Outlined.HandleBottom
                Edge.Right -> LudensIcons.Outlined.HandleRight
                else -> LudensIcons.Outlined.HandleLeft
            },
            contentDescription = null,
            tint = tint,
        )
    }
}

/**
 * The floating opener button.
 *
 * This button controls both the dock state and floating state.
 *
 * @param dockState The dock state controlling the docking behavior.
 * @param floatingState The floating state managing the open/closed status.
 * @param modifier The modifier to apply to the button.
 *
 * @see DockState.standby
 * @see DockState.static
 * @see FloatingState.toggle
 */
@Composable
fun FloatingDockOpenerButton(
    dockState: DockState,
    floatingState: FloatingState,
    modifier: Modifier = Modifier,
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = {
            if (dockState.isStatic && floatingState.isOpen) {
                dockState.standby()
            } else dockState.static()
            floatingState.toggle()
        }
    ) {
        AnimatedVisibility(
            visible = floatingState.isOpen,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            val rotation by transition.animateFloat(label = "rotation") {
                if (it == EnterExitState.Visible) 0f else 360f
            }

            Icon(
                imageVector = LudensIcons.Default.Dismiss,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotation)
            )
        }

        AnimatedVisibility(
            visible = !floatingState.isOpen,
            enter = scaleIn(
                spring(
                    dampingRatio = 0.4f,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
            exit = ExitTransition.None
        ) {
            Icon(
                imageVector = LudensIcons.Outlined.Circle,
                contentDescription = null
            )
        }
    }
}

/**
 * A dockable floating component that combines docking behavior with a floating content.
 *
 * @param modifier The modifier to apply to the container.
 * @param dockState The state managing the docking behavior and mode.
 * @param timeout The timeout in milliseconds before automatically docking when in standby mode.
 * @param animationSpec The animation specification for the offset movement.
 * @param strategy The strategy for determining which edge to dock to.
 * @param floatingState The state managing the floating open/closed status.
 * @param handleButton The button composable displayed when docked, typically showing a handle icon.
 * @param openerButton The button composable displayed when undocked, used to toggle the floating.
 * @param content The floating content displayed when open.
 *
 * @see DockLayout
 * @see Floating
 * @see DockState
 * @see FloatingState
 * @see DockStrategy
 */
@Composable
fun FloatingDock(
    modifier: Modifier = Modifier,
    dockState: DockState = rememberDockState(),
    timeout: Long = 3000L,
    animationSpec: AnimationSpec<IntOffset> = spring(
        stiffness = Spring.StiffnessLow,
        dampingRatio = Spring.DampingRatioLowBouncy
    ),
    strategy: DockStrategy = DockStrategy.Auto,
    floatingState: FloatingState = rememberFloatingState(),
    handleButton: @Composable (DockState) -> Unit = ::FloatingDockHandleButton,
    openerButton: @Composable (DockState, FloatingState) -> Unit = ::FloatingDockOpenerButton,
    content: @Composable ((FloatingState) -> Unit),
) {
    DockLayout(
        modifier = modifier,
        state = dockState,
        timeout = timeout,
        animationSpec = animationSpec,
        strategy = strategy
    ) {
        val isDocked = dockState.isDocked
        AnimatedVisibility(
            visible = isDocked,
            enter = fadeIn(
                tween(delayMillis = 600)
            ),
            exit = ExitTransition.None,
        ) {
            handleButton(dockState)
        }

        val reverseEdge = dockState.edge.reverse
        AnimatedVisibility(
            visible = !isDocked,
            enter = rememberEnterTransition(reverseEdge),
            exit = rememberExitTransition(reverseEdge)
        ) {
            Floating(
                state = floatingState,
                strategy = reverseEdge.toFloatingStrategy(),
                opener = {
                    openerButton(dockState, it)
                },
                content = content
            )
        }
    }
}

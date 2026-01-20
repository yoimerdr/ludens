package com.yoimerdr.compose.ludens.features.settings.presentation.secction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoimerdr.compose.ludens.app.theme.outlineDark
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.PositionableControlItem
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.getEnabled
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.withPositionable
import com.yoimerdr.compose.ludens.features.settings.presentation.components.KeyActionButton
import com.yoimerdr.compose.ludens.features.settings.presentation.components.OptionCard
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.drag.clampedDraggable
import com.yoimerdr.compose.ludens.ui.components.drag.clampedDraggableSource
import com.yoimerdr.compose.ludens.ui.components.layout.FloatingDock
import com.yoimerdr.compose.ludens.ui.components.layout.FloatingDockHandleButton
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Circle
import com.yoimerdr.compose.ludens.ui.icons.outlined.Dismiss
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import com.yoimerdr.compose.ludens.ui.state.drag.drag
import com.yoimerdr.compose.ludens.ui.state.drag.rememberClampedDraggableState
import com.yoimerdr.compose.ludens.ui.state.layout.DockMode
import com.yoimerdr.compose.ludens.ui.state.layout.rememberAutoDockState
import io.github.yoimerdr.compose.virtualjoystick.core.control.BackgroundType
import io.github.yoimerdr.compose.virtualjoystick.ui.view.JoystickBackground
import org.koin.compose.viewmodel.koinViewModel


/**
 * The draggable settings button control.
 *
 * @param settings The settings control item with position data.
 * @param showFloatingActions Whether to show the floating actions menu instead of settings button.
 * @param onEvent Callback invoked when the control position is updated.
 */
@Composable
private fun BoxScope.MovableSettings(
    settings: PositionableControlItem,
    showFloatingActions: Boolean,
    onEvent: (SettingsEvent.UpdateControlPosition) -> Unit,
) {
    val item = settings.first
    val state = rememberAutoDockState(initialState = DockMode.Idle)
    val dragger = rememberClampedDraggableState(
        initialOffset = Offset(settings.first.x, settings.first.y),
        onStart = {
            if (showFloatingActions) {
                state.static()
            }
        },
        onEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = item.type, x = it.x, y = it.y
                )
            )
            if (showFloatingActions) {
                state.standby()
            }
        }
    )

    val draggable = Modifier.align(Alignment.TopEnd)
        .clampedDraggableSource(dragger)
        .offset { dragger.offset.round() }
        .clampedDraggable(dragger)


    if (showFloatingActions) {
        LaunchedEffect(state.mode) {
            if (state.mode == DockMode.Idle)
                dragger.drag(state.edge)
        }

        FloatingDock(
            modifier = draggable,
            dockState = state,
            handleButton = {
                FloatingDockHandleButton(
                    state = it,
                    tint = outlineDark.copy(
                        alpha = settings.second.alpha
                    )
                )
            },
            openerButton = { _, _ ->
                KeyActionButton(
                    modifier = Modifier
                        .alpha(settings.second.alpha)
                ) {
                    Icon(
                        LudensIcons.Outlined.Circle,
                        contentDescription = "Actions",
                    )
                }
            }
        ) {}
    } else {
        KeyActionButton(
            modifier = Modifier.alpha(settings.second.alpha)
                .then(draggable)
        ) {
            Icon(
                LudensIcons.Default.Settings,
                contentDescription = "Settings",
            )
        }
    }
}

/**
 * The draggable joystick control.
 *
 * @param joystick The joystick control item with position data.
 * @param onEvent Callback invoked when the control position is updated.
 */
@Composable
private fun BoxScope.MovableJoystick(
    joystick: PositionableControlItem,
    onEvent: (SettingsEvent.UpdateControlPosition) -> Unit,
) {
    val item = joystick.first
    val dragger = rememberClampedDraggableState(
        initialOffset = Offset(item.x, item.y),
        onEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = item.type, x = it.x, y = it.y
                )
            )
        }
    )

    Box(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .clampedDraggableSource(dragger)
            .offset { dragger.offset.round() }
            .clampedDraggable(dragger)
    ) {
        JoystickBackground(
            type = BackgroundType.DpadModern,
            modifier = Modifier
                .size(150.dp)
                .alpha(joystick.second.alpha)
        )
    }
}

/**
 * A close button to exit control movement mode.
 *
 * @param onCloseClick Callback invoked when the close button is clicked.
 */
@Composable
private fun BoxScope.MovableActions(
    visible: Boolean = true,
    containerSize: IntSize,
    padSize: Dp = 150.dp,
    onCloseClick: (() -> Unit)?,
    onEvent: (SettingsEvent) -> Unit,
) {
    val shape = MaterialTheme.shapes.small

    val sizePx = with(LocalDensity.current) {
        padSize.toPx()
    }

    AnimatedVisibility(
        modifier = Modifier.align(Alignment.TopStart),
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        OptionCard(
            padding = PaddingValues.Zero,
            elevation = CardDefaults.cardElevation(),
            shape = shape
        ) {
            FlowRow(
            ) {
                IconButton(
                    shape = shape,
                    onClick = {
                        onCloseClick?.invoke()
                    }
                ) {
                    Icon(
                        LudensIcons.Default.Dismiss,
                        contentDescription = "Close",
                    )
                }
                IconButton(
                    shape = shape,
                    onClick = {
                        onEvent(
                            SettingsEvent.SwapControlPositions(
                                item = PositionableType.Joystick to PositionableType.Keys,
                                bounds = Rect(
                                    left = 0f,
                                    top = containerSize.height - sizePx,
                                    right = containerSize.width - sizePx,
                                    bottom = containerSize.height - sizePx
                                )
                            )
                        )
                    }
                ) {
                    Text("S")
                }
                IconButton(
                    shape = shape,
                    onClick = {
                        onEvent(
                            SettingsEvent.RestoreDefaultControlPositions()
                        )
                    }
                ) {
                    Text("R")
                }
            }
        }

    }
}

/**
 * The draggable group of key controls arranged in a cross pattern.
 *
 * @param items The list of key control items with position data.
 * @param onEvent Callback invoked when the control position is updated.
 */
@Composable
private fun BoxScope.MovableKeyControls(
    items: List<PositionableControlItem>,
    onEvent: (SettingsEvent) -> Unit,
) {
    val positions = listOf(
        Alignment.CenterEnd,
        Alignment.BottomCenter,
        Alignment.TopCenter,
        Alignment.CenterStart,
    )

    val item = items.first().first
    val dragger = rememberClampedDraggableState(
        initialOffset = Offset(item.x, item.y),
        onEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = PositionableType.Keys, x = it.x, y = it.y
                )
            )
        }
    )

    Box(
        modifier = Modifier.align(Alignment.BottomEnd)
            .size(150.dp)
            .clampedDraggableSource(dragger)
            .offset { dragger.offset.round() }
            .clampedDraggable(dragger)
    ) {
        items.zip(positions).forEach {
            KeyActionButton(
                text = it.first.second.type.simpleName,
                modifier = Modifier.align(it.second).alpha(it.first.second.alpha)
            )
        }
    }
}

/**
 * The movable controls section for repositioning control elements.
 *
 * @param items The list of positionable control items.
 * @param showControls Whether to show controls other than settings button.
 * @param showFloatingActions Whether to show the floating actions instead of settings button.
 * @param onEvent Callback invoked when a settings event occurs.
 */
@Composable
private fun BoxScope.MovableControls(
    items: List<PositionableControlItem>,
    showControls: Boolean = true,
    showFloatingActions: Boolean = true,
    onEvent: (SettingsEvent) -> Unit,
) {
    val settings = items.getEnabled(ItemType.Settings).firstOrNull()

    if (settings != null) {
        MovableSettings(settings, showFloatingActions, onEvent)
    }

    val joystick = items.getEnabled(ItemType.Joystick).firstOrNull()
    if (joystick != null && showControls) {
        MovableJoystick(joystick, onEvent)
    }

    val keyControls = ItemType.keys
    val keyItems = items.getEnabled(keyControls.first(), *keyControls.drop(1).toTypedArray())

    if (keyItems.isNotEmpty() && showControls) {
        MovableKeyControls(
            keyItems,
            onEvent,
        )
    }
}

/**
 * The movable controls section for repositioning control elements.
 *
 * @param items The list of positionable control items.
 * @param showControls Whether to show controls other than settings button.
 * @param showFloatingActions Whether to show the floating actions instead of settings button.
 * @param onCloseClick Callback invoked when the close button is clicked to exit movement mode.
 * @param onEvent Callback invoked when a settings event occurs.
 */
@Composable
fun MovableControlsSettingsSection(
    items: List<PositionableControlItem>,
    showControls: Boolean = true,
    showFloatingActions: Boolean = true,
    onCloseClick: (() -> Unit)? = null,
    onEvent: (SettingsEvent) -> Unit,
) {
    var showActions by remember { mutableStateOf(true) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = {
                    showActions = !showActions
                }
            )
    ) {
        MovableControls(
            items = items,
            showControls = showControls,
            showFloatingActions = showFloatingActions,
            onEvent = onEvent
        )
        MovableActions(
            visible = showActions,
            onCloseClick = onCloseClick,
            onEvent = onEvent,
            containerSize = IntSize(
                constraints.maxWidth,
                constraints.maxHeight
            )
        )
    }

}

/**
 * The movable controls section with view model integration.
 *
 * @param viewModel The settings view model.
 * @param onCloseClick Callback invoked when the close button is clicked to exit movement mode.
 */
@Composable
fun MovableControlsSettingsSection(
    viewModel: SettingsViewModel = koinViewModel(),
    onCloseClick: (() -> Unit)? = null,
) {
    val controls by viewModel.controlState.collectAsStateWithLifecycle()
    val actions by viewModel.actionState.collectAsStateWithLifecycle()

    MovableControlsSettingsSection(
        showControls = controls.enabled,
        items = controls.items.withPositionable(controls.positions),
        onEvent = viewModel::onEvent,
        showFloatingActions = actions.items.size > 1,
        onCloseClick = onCloseClick
    )
}
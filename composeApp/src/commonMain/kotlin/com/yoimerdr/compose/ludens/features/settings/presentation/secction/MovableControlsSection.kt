package com.yoimerdr.compose.ludens.features.settings.presentation.secction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.PositionableControlItem
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.getEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.components.CloseIconButton
import com.yoimerdr.compose.ludens.features.settings.presentation.components.KeyActionButton
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.ui.components.drag.Draggable
import com.yoimerdr.compose.ludens.ui.components.drag.DraggableMode
import com.yoimerdr.compose.ludens.ui.components.drag.DraggablePosition
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.outlined.Settings
import io.github.yoimerdr.compose.virtualjoystick.core.control.BackgroundType
import io.github.yoimerdr.compose.virtualjoystick.ui.view.JoystickBackground

/**
 * The draggable settings button control.
 *
 * @param settings The settings control item with position data.
 * @param onEvent Callback invoked when the control position is updated.
 */
@Composable
private fun BoxScope.MovableSettings(
    settings: PositionableControlItem,
    onEvent: (SettingsEvent.UpdateControlPosition) -> Unit,
) {
    val item = settings.first

    Draggable(
        enable = true,
        modifier = Modifier.align(Alignment.TopEnd),
        position = DraggablePosition.None,
        mode = DraggableMode.IconAndContent,
        offset = Offset(item.x, item.y),
        onDragEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = item.type, x = it.x, y = it.y
                )
            )
        }) {

        KeyActionButton {
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
    Draggable(
        enable = true,
        position = DraggablePosition.None,
        mode = DraggableMode.IconAndContent,
        offset = Offset(item.x, item.y),
        onDragEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = item.type, x = it.x, y = it.y
                )
            )
        },
        modifier = Modifier.align(Alignment.BottomStart)
    ) {
        JoystickBackground(
            type = BackgroundType.DpadModern,
            modifier = Modifier.alpha(joystick.second.alpha).size(150.dp)
        )
    }
}

/**
 * A close button to exit control movement mode.
 *
 * @param onEvent Callback invoked when the close button is clicked.
 */
@Composable
private fun BoxScope.CloseMovables(
    onEvent: (SettingsEvent.UpdateControlMovementMode) -> Unit,
) {
    CloseIconButton(
        modifier = Modifier.align(Alignment.TopStart)
    ) {
        onEvent(
            SettingsEvent.UpdateControlMovementMode(
                enabled = false
            )
        )
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

    Draggable(
        enable = true,
        position = DraggablePosition.None,
        mode = DraggableMode.IconAndContent,
        offset = Offset(item.x, item.y),
        onDragEnd = {
            onEvent(
                SettingsEvent.UpdateControlPosition(
                    type = PositionableType.Keys, x = it.x, y = it.y
                )
            )
        },
        modifier = Modifier.align(Alignment.BottomEnd)
    ) {
        Box(
            modifier = Modifier.size(150.dp)
        ) {
            items.zip(positions).forEach {
                KeyActionButton(
                    text = it.first.second.type.simpleName,
                    modifier = Modifier.align(it.second).alpha(it.first.second.alpha)
                )
            }
        }
    }
}

/**
 * The movable controls section for repositioning control elements.
 *
 * @param showControls Whether to show controls other than settings button.
 * @param items The list of positionable control items.
 * @param onEvent Callback invoked when a settings event occurs.
 */
@Composable
fun BoxScope.MovableControlsSettingsSection(
    showControls: Boolean = true,
    items: List<PositionableControlItem>,
    onEvent: (SettingsEvent) -> Unit,
) {

    val settings = items.getEnabled(ItemType.Settings).firstOrNull()

    if (settings != null) {
        MovableSettings(settings, onEvent)
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

    CloseMovables(onEvent)
}
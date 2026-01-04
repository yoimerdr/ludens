package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toInputKey
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.PositionableControlItem
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.ui.components.buttons.OutlinedIconButton
import kotlin.math.roundToInt

/**
 * Displays an action button that detects press and release gestures.
 *
 * The button shows text in the center and emits key events for both press (Down) and
 * release (Up) actions. It includes ripple interaction feedback for visual response
 * to user touch events.
 *
 * @param modifier The modifier to be applied to the button
 * @param text The text to display in the center of the button
 * @param onClick Callback invoked with [KeyEventType.Down] on press and [KeyEventType.Up] on release
 */
@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (KeyEventType) -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    OutlinedIconButton(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .indication(source, ripple())
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            onClick(KeyEventType.Down)
                            source.emit(PressInteraction.Press(it))
                            try {
                                awaitRelease()
                            } finally {
                                source.emit(PressInteraction.Release(PressInteraction.Press(it)))
                                onClick(KeyEventType.Up)
                            }
                        },
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text, style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

/**
 * Displays a group of action control buttons for game input.
 *
 * This composable creates a container with up to 4 action buttons positioned
 * at the cardinal directions (center-end, bottom-center, top-center, center-start).
 * Each button corresponds to a key control item and triggers input events when pressed
 * or released.
 *
 * Only enabled key control items are rendered. If no enabled key items are provided,
 * nothing is displayed.
 *
 * The buttons are positioned based on the first item's coordinates, and each button's
 * appearance (alpha transparency) is controlled by its individual settings. The actual
 * key binding can be customized per button, falling back to default input keys if not
 * specified.
 *
 * @param modifier The modifier to be applied to the container box
 * @param player The input player instance that handles key events
 * @param items List of positionable control items containing position, key mappings, and visual settings.
 * Only items with types in [ItemType.keys] and enabled state will be displayed.
 * @param onEvent Callback invoked when a control key is pressed or released, passing a [HomeEvent.OnClickControlKey]
 */
@Composable
fun ActionControls(
    modifier: Modifier = Modifier,
    player: InputPlayer,
    items: List<PositionableControlItem>,
    onEvent: (HomeEvent) -> Unit,
) {
    val keyControls = ItemType.keys
    val keyItems = items.filter { it.second.type in keyControls && it.second.enabled }

    if (keyItems.isEmpty()) {
        return
    }

    val position = keyItems.first().first

    val defaults = keyItems.mapNotNull { it.second.type.toInputKey() }
        .zip(
            listOf(
                Alignment.CenterEnd,
                Alignment.BottomCenter,
                Alignment.TopCenter,
                Alignment.CenterStart,
            )
        )

    Box(
        modifier = modifier.size(150.dp)
            .offset {
                IntOffset(
                    x = position.x.roundToInt(),
                    y = position.y.roundToInt(),
                )
            }
    ) {
        keyItems.zip(defaults).forEach {
            val item = it.first.second
            key(item) {
                ActionButton(
                    text = item.type.simpleName,
                    modifier = Modifier.alpha(item.alpha)
                        .align(it.second.second)
                ) { type ->
                    val key = (item as? ControlKeyItemState)?.key ?: it.second.first
                    onEvent(
                        HomeEvent.OnClickControlKey(
                            player = player,
                            item = item,
                            type = type,
                            key = key,
                        )
                    )
                }
            }
        }
    }
}
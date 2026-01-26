package com.yoimerdr.compose.ludens.features.home.presentation.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.app.ui.providers.LocalInputPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toEvent
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toInputKey
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.features.home.presentation.components.KeyControlButton
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import kotlin.math.roundToInt


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
 * The container's position is determined by the [position] parameter. Each button's
 * appearance (alpha transparency) is controlled by its individual settings in [items].
 * The actual key binding can be customized per button, falling back to default input keys if not
 * specified.
 *
 * @param modifier The modifier to be applied to the container box
 * @param position The position state defining the coordinates (x, y) of the controls container
 * @param items List of control items containing key mappings and visual settings (alpha)
 * @param onEvent Callback invoked when a key control event occurs
 */
@Composable
fun KeyControls(
    modifier: Modifier = Modifier,
    position: PositionableItemState,
    items: List<ControlItemState>,
    onEvent: (HomeEvent) -> Unit,
) {
    if (items.isEmpty()) {
        return
    }

    val player = LocalInputPlayer.current

    val defaults = items.mapNotNull { it.type.toInputKey() }
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
        items.zip(defaults).forEach {
            val item = it.first
            KeyControlButton(
                text = item.type.simpleName,
                modifier = Modifier.alpha(item.alpha)
                    .align(it.second.second)
            ) { type ->
                val key = (item as? ControlKeyItemState)?.key ?: it.second.first

                player.onKeyEvent(
                    key.toEvent(
                        type = type
                    )
                )

                onEvent(
                    HomeEvent.OnClickControlKey(
                        item = item,
                        type = type,
                        key = key,
                    )
                )
            }
        }
    }
}
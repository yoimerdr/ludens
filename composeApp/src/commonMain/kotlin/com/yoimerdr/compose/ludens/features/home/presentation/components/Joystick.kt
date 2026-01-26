package com.yoimerdr.compose.ludens.features.home.presentation.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.app.ui.providers.LocalMovementsPlayer
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.down
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.downLeft
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.downRight
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.left
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.none
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.right
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.up
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.upLeft
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.upRight
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.PositionableItemState
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.filled.DpadDown
import com.yoimerdr.compose.ludens.ui.icons.filled.DpadLeft
import com.yoimerdr.compose.ludens.ui.icons.filled.DpadRight
import com.yoimerdr.compose.ludens.ui.icons.filled.DpadUp
import io.github.yoimerdr.compose.virtualjoystick.core.control.BackgroundType
import io.github.yoimerdr.compose.virtualjoystick.core.control.Direction
import io.github.yoimerdr.compose.virtualjoystick.core.control.DirectionType
import io.github.yoimerdr.compose.virtualjoystick.ui.scope.draw.image.drawDirectionalImage
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickMoveEnd
import io.github.yoimerdr.compose.virtualjoystick.ui.view.VirtualJoystick
import io.github.yoimerdr.compose.virtualjoystick.ui.view.rememberJoystickEventHolder
import io.github.yoimerdr.compose.virtualjoystick.ui.view.rememberJoystickState
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlin.math.roundToInt

/**
 * Displays a virtual joystick control for player movement.
 *
 * The joystick uses directional D-pad icons (up, down, left, right).
 * Movement events are tracked and emitted through the [onEvent]
 * callback only when the joystick direction changes.
 *
 * Player movement interactions are handled directly in this component
 * through the [LocalMovementsPlayer], while events are emitted via [onEvent]
 * for state coordination and tracking.
 *
 * @param modifier The modifier to be applied to the joystick container
 * @param onEvent Callback invoked when a movement event occurs, passing a [HomeEvent.OnPlayerMovement]
 * @param joystick Optional control item configuration that defines the joystick's
 * appearance (enabled state, alpha transparency). If null or disabled, the joystick will not be displayed.
 * @param position The position state for the joystick defining its coordinates (x, y).
 * @param directions The type of directions supported by the joystick. Defaults to [DirectionType.Complete]
 * which enables all 8 directional movements (including diagonals).
 */
@Composable
fun Joystick(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent.OnPlayerMovement) -> Unit,
    joystick: ControlItemState?,
    position: PositionableItemState,
    directions: DirectionType = DirectionType.Complete,
) {
    // null settings mean not enabled
    if (joystick == null || !joystick.enabled)
        return

    val player = LocalMovementsPlayer.current
    val state = rememberJoystickState(directionType = directions)
    val holder = rememberJoystickEventHolder(state)

    val up = rememberVectorPainter(LudensIcons.Filled.DpadUp)
    val down = rememberVectorPainter(LudensIcons.Filled.DpadDown)
    val left = rememberVectorPainter(LudensIcons.Filled.DpadLeft)
    val right = rememberVectorPainter(LudensIcons.Filled.DpadRight)

    LaunchedEffect(holder) {
        holder.events
            .distinctUntilChangedBy { it.snapshot.direction }
            .collect { event ->
                // Handle player movement directly in the UI layer
                val type = when (event) {
                    is JoystickMoveEnd -> KeyEventType.Up
                    else -> KeyEventType.Down
                }

                when (event.snapshot.direction) {
                    Direction.Up -> player.up(type)
                    Direction.Down -> player.down(type)
                    Direction.Left -> player.left(type)
                    Direction.Right -> player.right(type)
                    Direction.UpLeft -> player.upLeft(type)
                    Direction.UpRight -> player.upRight(type)
                    Direction.DownLeft -> player.downLeft(type)
                    Direction.DownRight -> player.downRight(type)
                    else -> player.none()
                }

                // Emit event to ViewModel for state tracking
                onEvent(
                    HomeEvent.OnPlayerMovement(
                        movement = event,
                    )
                )
            }

    }

    VirtualJoystick(
        state = state,
        holder = holder,
        backgroundType = BackgroundType.DpadModern,
        modifier = modifier.size(150.dp)
            .offset {
                IntOffset(position.x.roundToInt(), position.y.roundToInt())
            }
            .alpha(joystick.alpha)
    ) {
        drawDirectionalImage {
            when (it) {
                Direction.Up -> listOf(up)
                Direction.Down -> listOf(down)
                Direction.Left -> listOf(left)
                Direction.Right -> listOf(right)
                Direction.UpLeft -> listOf(up, left)
                Direction.UpRight -> listOf(up, right)
                Direction.DownLeft -> listOf(down, left)
                Direction.DownRight -> listOf(down, right)
                else -> null
            }
        }
    }
}
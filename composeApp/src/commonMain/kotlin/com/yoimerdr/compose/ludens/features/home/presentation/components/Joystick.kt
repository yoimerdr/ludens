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
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.PositionableControlItem
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
 * If [joystick] is null or disabled, the joystick will not be rendered.
 *
 * @param modifier The modifier to be applied to the joystick container
 * @param onEvent Callback invoked when a movement event occurs, passing a [HomeEvent.OnPlayerMovement]
 * @param joystick Optional positionable control item configuration that defines the joystick's
 * position (x, y coordinates) and appearance (enabled state, alpha transparency).
 * If null or disabled, the joystick will not be displayed.
 * @param directions The type of directions supported by the joystick. Defaults to [DirectionType.Complete]
 * which enables all 8 directional movements (including diagonals).
 */
@Composable
fun Joystick(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    joystick: PositionableControlItem?,
    directions: DirectionType = DirectionType.Complete,
) {
    // null settings mean not enabled
    if (joystick == null || !joystick.second.enabled)
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
            .collect {
                onEvent(
                    HomeEvent.OnPlayerMovement(
                        player = player,
                        movement = it,
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
                val positions = joystick.first
                IntOffset(positions.x.roundToInt(), positions.y.roundToInt())
            }
            .alpha(joystick.second.alpha)
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
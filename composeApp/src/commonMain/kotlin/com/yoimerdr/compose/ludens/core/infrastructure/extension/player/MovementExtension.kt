package com.yoimerdr.compose.ludens.core.infrastructure.extension.player

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toMovementEvent

/**
 * Configures movement key events for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key events in milliseconds
 * @param up The movement key event for upward movement
 * @param down The movement key event for downward movement
 * @param left The movement key event for leftward movement
 * @param right The movement key event for rightward movement
 */
fun MovementsPlayer.movements(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
    up: MovementKeyEvent = InputKey.Up.toMovementEvent(type, timeout),
    down: MovementKeyEvent = InputKey.Down.toMovementEvent(type, timeout),
    left: MovementKeyEvent = InputKey.Left.toMovementEvent(type, timeout),
    right: MovementKeyEvent = InputKey.Right.toMovementEvent(type, timeout),
) {
    onKeyEvent(up, down, left, right)
}

/**
 * Triggers an upward movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key event in milliseconds
 */
fun MovementsPlayer.up(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    movements(
        type = KeyEventType.Up,
        up = InputKey.Up.toMovementEvent(type, timeout),
    )
}

/**
 * Triggers a downward movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key event in milliseconds
 */
fun MovementsPlayer.down(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    movements(
        type = KeyEventType.Up,
        down = InputKey.Down.toMovementEvent(type, timeout),
    )
}

/**
 * Triggers a leftward movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key event in milliseconds
 */
fun MovementsPlayer.left(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    movements(
        type = KeyEventType.Up,
        left = InputKey.Left.toMovementEvent(type, timeout),
    )
}

/**
 * Triggers a rightward movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key event in milliseconds
 */
fun MovementsPlayer.right(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    movements(
        type = KeyEventType.Up,
        right = InputKey.Right.toMovementEvent(type, timeout),
    )
}

/**
 * Releases all movement keys for the player.
 *
 * @param timeout Optional timeout duration for the key events in milliseconds
 */
fun MovementsPlayer.none(timeout: Int? = null) {
    movements(
        type = KeyEventType.Up,
        timeout = timeout,
    )
}

/**
 * Triggers a diagonal upward-left movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key events in milliseconds
 */
fun MovementsPlayer.upLeft(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    val (up, left) = InputKey.upLeft.map {
        it.toMovementEvent(
            type = type,
            timeout = timeout,
        )
    }
    movements(
        type = KeyEventType.Up,
        up = up,
        left = left,
    )
}

/**
 * Triggers a diagonal upward-right movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key events in milliseconds
 */
fun MovementsPlayer.upRight(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    val (up, right) = InputKey.upRight.map {
        it.toMovementEvent(
            type = type,
            timeout = timeout,
        )
    }
    movements(
        type = KeyEventType.Up,
        up = up,
        right = right,
    )
}

/**
 * Triggers a diagonal downward-left movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key events in milliseconds
 */
fun MovementsPlayer.downLeft(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    val (down, left) = InputKey.downLeft.map {
        it.toMovementEvent(
            type = type,
            timeout = timeout,
        )
    }
    movements(
        type = KeyEventType.Up,
        down = down,
        left = left,
    )
}

/**
 * Triggers a diagonal downward-right movement for the player.
 *
 * @param type The type of key event (Down by default)
 * @param timeout Optional timeout duration for the key events in milliseconds
 */
fun MovementsPlayer.downRight(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
) {
    val (down, right) = InputKey.downRight.map {
        it.toMovementEvent(
            type = type,
            timeout = timeout,
        )
    }
    movements(
        type = KeyEventType.Up,
        down = down,
        right = right,
    )
}

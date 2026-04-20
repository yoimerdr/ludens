package com.yoimerdr.compose.ludens.core.infrastructure.extension.key

import com.yoimerdr.compose.ludens.core.domain.model.key.GraphicsKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.InputKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.GraphicsKey
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey

/**
 * Converts an [ItemType] to its corresponding [InputKey].
 *
 * Maps game item types to keyboard input keys:
 * - KeyA -> Enter
 * - KeyB -> Escape
 * - KeyX -> Shift
 * - KeyY -> Control
 *
 * This can be used as default input key for the keys controls.
 *
 * @return The corresponding [InputKey], or null if the ItemType doesn't map to an input key.
 */
fun ItemType.toInputKey(): InputKey? {
    return when (this) {
        ItemType.KeyA -> InputKey.Enter
        ItemType.KeyB -> InputKey.Escape
        ItemType.KeyX -> InputKey.Shift
        ItemType.KeyY -> InputKey.Control
        else -> null
    }
}

/**
 * Converts a [GraphicsKey] to a [GraphicsKeyEvent].
 *
 * Creates a graphics key event with the specified type and optional timeout.
 *
 * @param type The type of key event (Down or Up). Defaults to [KeyEventType.Down].
 * @param timeout Optional delay in milliseconds before the event is triggered.
 * @return A [GraphicsKeyEvent] with the specified parameters.
 */
fun GraphicsKey.toEvent(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
): GraphicsKeyEvent =
    GraphicsKeyEvent(type = type, code = code, timeout = timeout)

/**
 * Converts an [InputKey] to an [InputKeyEvent].
 *
 * Creates an input key event with the specified type and optional timeout.
 *
 * @param type The type of key event (Down or Up). Defaults to [KeyEventType.Down].
 * @param timeout Optional delay in milliseconds before the event is triggered.
 * @return An [InputKeyEvent] with the specified parameters.
 */
fun InputKey.toEvent(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
): InputKeyEvent = InputKeyEvent(
    type = type,
    code = code,
    timeout = timeout
)

/**
 * Converts an [InputKey] to a [MovementKeyEvent].
 *
 * Creates a movement key event for directional input handling.
 * This function validates that the input key is a valid movement key
 * before conversion.
 *
 * @param type The type of key event (Down or Up). Defaults to [KeyEventType.Down].
 * @param timeout Optional delay in milliseconds before the event is triggered.
 * @return A [MovementKeyEvent] with the specified parameters.
 * @throws IllegalArgumentException if the InputKey is not a valid movement key.
 * @see InputKey.movements
 */
fun InputKey.toMovementEvent(
    type: KeyEventType = KeyEventType.Down,
    timeout: Int? = null,
): MovementKeyEvent {
    if (this !in InputKey.movements) {
        throw IllegalArgumentException("InputKey $this is not a movement key")
    }

    return MovementKeyEvent(
        code = code,
        type = type,
        timeout = timeout,
    )
}
package com.yoimerdr.compose.ludens.core.domain.model.key

/**
 * Represents a key event for movement controls (directional input).
 *
 * @param code The key code for the movement.
 * @param type The type of event (Up or Down).
 * @param timeout Optional timeout value in milliseconds.
 */
class MovementKeyEvent(
    code: Int,
    type: KeyEventType,
    timeout: Int?,
) : KeyEvent(code, type, timeout) {
    override fun copy(
        code: Int,
        type: KeyEventType,
        timeout: Int?,
    ): MovementKeyEvent = MovementKeyEvent(code, type, timeout)
}
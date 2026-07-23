package com.yoimerdr.compose.ludens.core.domain.model.key

/**
 * Represents a key event for input actions (buttons like A, B, X, Y).
 *
 * @param code The key code for the input action.
 * @param type The type of event (Up or Down).
 * @param timeout Optional timeout value in milliseconds.
 */
class InputKeyEvent(
    code: Int,
    type: KeyEventType,
    timeout: Int?,
) : KeyEvent(code, type, timeout) {
    override fun copy(
        code: Int,
        type: KeyEventType,
        timeout: Int?,
    ): InputKeyEvent = InputKeyEvent(code, type, timeout)
}
package com.yoimerdr.compose.ludens.core.domain.model.key

/**
 * Represents a key event for graphics-related actions.
 *
 * @param code The key code for the graphics action.
 * @param type The type of event (Up or Down).
 * @param timeout Optional timeout value in milliseconds.
 */
class GraphicsKeyEvent(
    code: Int,
    type: KeyEventType,
    timeout: Int?,
) : KeyEvent(code, type, timeout) {
    override fun copy(
        code: Int,
        type: KeyEventType,
        timeout: Int?,
    ): GraphicsKeyEvent = GraphicsKeyEvent(code, type, timeout)
}
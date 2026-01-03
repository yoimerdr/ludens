package com.yoimerdr.compose.ludens.core.domain.port

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType

/**
 * Type alias for a function that declares a key event script.
 *
 * @param KeyEventType The type of key event (Up or Down).
 * @param Int? Optional timeout value for the event.
 * @return The constructed KeyEvent.
 */
typealias KeyScriptDeclaration = (KeyEventType, Int?) -> KeyEvent

/**
 * Port interface for evaluating key events.
 *
 * This interface defines the contract for preparing and evaluating key events,
 * allowing platform-specific implementations to handle key event processing.
 */
interface KeyEventEvaluator {

    /**
     * Evaluates the accumulated key event script.
     *
     * @param callback Optional callback to receive the script execution result.
     */
    fun evaluateKeyEventScript(callback: ((String) -> Unit)? = null)

    /**
     * Prepares a key event with timeout control.
     *
     * @param type The type of key event (Up or Down).
     * @param timeout Whether to include a timeout in the event.
     * @param builder Function to build the KeyEvent.
     */
    fun prepareKeyEvent(
        type: KeyEventType,
        timeout: Boolean,
        builder: KeyScriptDeclaration,
    )

    /**
     * Prepares a key event without timeout.
     *
     * @param type The type of key event (Up or Down).
     * @param builder Function to build the KeyEvent.
     */
    fun prepareKeyEvent(
        type: KeyEventType,
        builder: KeyScriptDeclaration,
    )

    /**
     * Evaluates a key event immediately with timeout control.
     *
     * @param type The type of key event (Up or Down).
     * @param timeout Whether to include a timeout in the event.
     * @param builder Function to build the KeyEvent.
     */
    fun evaluateKeyEvent(
        type: KeyEventType,
        timeout: Boolean,
        builder: KeyScriptDeclaration,
    )

    /**
     * Evaluates a key event immediately without timeout.
     *
     * @param type The type of key event (Up or Down).
     * @param builder Function to build the KeyEvent.
     */
    fun evaluateKeyEvent(
        type: KeyEventType,
        builder: KeyScriptDeclaration,
    )

    /**
     * Evaluates a key event immediately.
     *
     * @param key The key event to evaluate.
     */
    fun evaluateKeyEvent(key: KeyEvent)
}
package com.yoimerdr.compose.ludens.core.domain.port.player

import com.yoimerdr.compose.ludens.core.domain.model.key.InputKeyEvent

/**
 * Port interface for handling input actions in the player.
 *
 * This interface defines the contract for processing button input events
 * (such as A, B, X, Y buttons).
 */
interface InputPlayer {
    /**
     * Handles an input action event.
     *
     * @param key The input key event to process.
     * @param pressed Whether the key is pressed (true) or released (false).
     */
    fun onKeyEvent(key: InputKeyEvent, pressed: Boolean)

    /**
     * Handles an input action event.
     *
     * @param key The input key event to process.
     */
    fun onKeyEvent(key: InputKeyEvent)
}


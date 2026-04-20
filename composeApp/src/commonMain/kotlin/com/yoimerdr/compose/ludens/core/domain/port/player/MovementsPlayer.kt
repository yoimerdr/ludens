package com.yoimerdr.compose.ludens.core.domain.port.player

import com.yoimerdr.compose.ludens.core.domain.model.key.MovementKeyEvent

/**
 * Port interface for handling movement input in the player.
 *
 * This interface defines the contract for processing directional movements,
 * including cardinal directions and diagonals.
 */
interface MovementsPlayer {
    /**
     * Handles a single dynamic movement key event.
     *
     * @param key The movement key event to process.
     * @param pressed True if the key is pressed, false if released.
     */
    fun onKeyEvent(key: MovementKeyEvent, pressed: Boolean)

    /**
     * Handles multiple movement key events simultaneously.
     *
     * This is useful for processing combined directional inputs,
     * or clear other key events.
     *
     * @param key The primary movement key event.
     * @param keys Additional movement key events to process together.
     */
    fun onKeyEvent(key: MovementKeyEvent, vararg keys: MovementKeyEvent)
}
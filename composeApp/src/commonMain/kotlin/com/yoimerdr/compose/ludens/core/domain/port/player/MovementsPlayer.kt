package com.yoimerdr.compose.ludens.core.domain.port.player

/**
 * Port interface for handling movement input in the player.
 *
 * This interface defines the contract for processing directional movements,
 * including cardinal directions and diagonals.
 */
interface MovementsPlayer {
    /**
     * Triggers upward movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun up(pressed: Boolean = false)

    /**
     * Triggers downward movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun down(pressed: Boolean = false)

    /**
     * Triggers leftward movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun left(pressed: Boolean = false)

    /**
     * Triggers rightward movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun right(pressed: Boolean = false)

    /**
     * Triggers upward-left diagonal movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun upLeft(pressed: Boolean = false)

    /**
     * Triggers upward-right diagonal movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun upRight(pressed: Boolean = false)

    /**
     * Triggers downward-left diagonal movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun downLeft(pressed: Boolean = false)

    /**
     * Triggers downward-right diagonal movement.
     * @param pressed Whether the movement is pressed (true) or released (false).
     * */
    fun downRight(pressed: Boolean = false)

    /** Triggers stop all movement input (no direction). */
    fun none()
}
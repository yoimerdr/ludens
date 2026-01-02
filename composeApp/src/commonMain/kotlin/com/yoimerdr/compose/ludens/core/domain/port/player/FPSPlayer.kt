package com.yoimerdr.compose.ludens.core.domain.port.player

/**
 * Port interface for controlling the FPS (frames per second) display.
 *
 * This interface defines the contract for showing, hiding, and toggling
 * the FPS counter in the player.
 */
interface FPSPlayer {
    /** Shows the FPS counter. */
    fun show()

    /** Hides the FPS counter. */
    fun hide()

    /** Toggles the FPS counter visibility. */
    fun toggle()

    /** Returns whether the FPS counter is currently visible. */
    val isVisible: Boolean
}
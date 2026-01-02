package com.yoimerdr.compose.ludens.core.domain.port.player

import com.yoimerdr.compose.ludens.core.domain.model.key.GraphicsKeyEvent

/**
 * Port interface for handling graphics-related events in the player.
 *
 * This interface defines the contract for processing graphics control events.
 */
interface GraphicPlayer {
    /**
     * Handles a graphics event.
     *
     * @param key The graphics key event to process.
     * @param pressed Whether the key is pressed (true) or released (false).
     */
    fun onKeyEvent(key: GraphicsKeyEvent, pressed: Boolean = false)
}
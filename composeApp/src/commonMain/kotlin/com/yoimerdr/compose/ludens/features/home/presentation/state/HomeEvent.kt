package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickEvent
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickMoveEnd
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickSnapshot

/**
 * Sealed interface representing events that can occur in the home screen.
 */
sealed interface HomeEvent {
    /**
     * Event triggered when player movement occurs via virtual joystick.
     *
     * @property player The movements player instance
     * @property movement The joystick event containing movement data
     */
    data class OnPlayerMovement(
        val player: MovementsPlayer,
        val movement: JoystickEvent,
    ) : HomeEvent {
        /**
         * The joystick snapshot containing position and movement data.
         */
        val snapshot: JoystickSnapshot
            get() = movement.snapshot

        /**
         * The key event type derived from the joystick movement state.
         * Returns [KeyEventType.Up] when movement ends, otherwise [KeyEventType.Down].
         */
        val type: KeyEventType
            get() = when (movement) {
                is JoystickMoveEnd -> KeyEventType.Up
                else -> KeyEventType.Down
            }
    }
}
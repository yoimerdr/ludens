package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.ui.state.PluginState
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickEvent
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickMoveEnd
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickSnapshot

/**
 * Sealed interface representing events that can occur in the home screen.
 */
sealed interface HomeEvent {

    /**
     * Event triggered when the FPS counter visibility is toggled.
     *
     * @property player The FPS player instance
     */
    data class ToggleFpsCounter(val player: FPSPlayer) : HomeEvent

    /**
     * Event triggered when the `YDP_Ludens` plugin has been loaded.
     *
     * @property plugin The loaded plugin state
     */
    data class OnPluginLoaded(val plugin: PluginState) : HomeEvent

    /**
     * Event triggered when a control key button is clicked.
     *
     * @property player The input player instance that handles key events
     * @property item The control item state containing the button configuration
     * @property type The type of key event (Down for press, Up for release)
     * @property key The input key that was triggered
     */
    data class OnClickControlKey(
        val player: InputPlayer,
        val item: ControlItemState,
        val type: KeyEventType,
        val key: InputKey,
    ) : HomeEvent

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
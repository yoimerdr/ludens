package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickEvent
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickMoveEnd
import io.github.yoimerdr.compose.virtualjoystick.ui.state.JoystickSnapshot

/**
 * Sealed interface representing events that can occur in the home screen.
 */
sealed interface HomeEvent {

    /**
     * Event triggered when a control key button is clicked.
     *
     * @property item The control item state containing the button configuration
     * @property type The type of key event (Down for press, Up for release)
     * @property key The input key that was triggered
     */
    data class OnClickControlKey(
        val item: ControlItemState,
        val type: KeyEventType,
        val key: InputKey,
    ) : HomeEvent

    /**
     * Event triggered when player movement occurs via virtual joystick.
     *
     * @property movement The joystick event containing movement data
     */
    data class OnPlayerMovement(
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

    /**
     * Event triggered when an action button is clicked.
     *
     * @property action The action item state containing the button configuration
     */
    data class OnClickAction(val action: ActionItemState) : HomeEvent

    /**
     * Event triggered to update the entry state based on web features.
     *
     * @property features The web features supported by the platform
     */
    data class UpdateEntry(
        val features: WebFeaturesState,
    ) : HomeEvent

    /**
     * Event triggered to toggle the FPS counter visibility.
     * The new state will be the opposite of the current value.
     */
    data object ToggleFpsCounter : HomeEvent

    /**
     * Event triggered to toggle the on-screen controls visibility.
     * The new state will be the opposite of the current value.
     */
    data object ToggleControlsVisibility : HomeEvent

    /**
     * Event triggered when a request is resolved.
     *
     * @property request The request that was resolved
     */
    data class ResolvedRequest(val request: HomeRequest) : HomeEvent

    /**
     * Event triggered when a request is rejected.
     *
     * @property request The request that was rejected
     */
    data class RejectedRequest(val request: HomeRequest) : HomeEvent
}
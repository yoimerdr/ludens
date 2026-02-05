package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

/**
 * Base interface for action settings events.
 */
sealed interface ActionSettingsEvent : SettingsEvent {
    /**
     * Event indicating an update to action settings.
     * */
    sealed interface UpdateSettings : ActionSettingsEvent,
        SettingsEvent.UpdateSettings
}

/**
 * Updates the enabled state of all action items.
 *
 * @param enabled Whether actions are enabled.
 */
data class UpdateActionsEnabled(val enabled: Boolean) : ActionSettingsEvent.UpdateSettings

/**
 * Updates the enabled state of a specific action item.
 *
 * @param index The action index.
 * @param enabled Whether the action is enabled.
 */
data class UpdateActionEnabled(
    val index: Int,
    val enabled: Boolean,
) : ActionSettingsEvent.UpdateSettings

/**
 * Swaps the order of two action items.
 *
 * @param indices A pair of indices representing the two actions to swap.
 */
data class SwapActionOrder(val indices: Pair<Int, Int>) : ActionSettingsEvent.UpdateSettings


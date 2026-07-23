package com.yoimerdr.compose.ludens.features.settings.presentation.state.events

import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection

/**
 * Events for the settings screen.
 */
sealed interface SettingsEvent {

    /**
     * Event indicating an update to settings.
     * */
    sealed interface UpdateSettings : SettingsEvent

    /**
     * Selects a settings section.
     *
     * @param section The section to select.
     */
    data class OnSelectSection(val section: SettingsSection) : SettingsEvent

    /**
     * Specific event related to navigation within the settings screen.
     * */
    data class NavigateTo(val destination: Destination) : SettingsEvent

    /**
     * Event indicating an attempt to update a setting.
     * */
    data class TryUpdate(
        val event: UpdateSettings,
    ) : SettingsEvent
}


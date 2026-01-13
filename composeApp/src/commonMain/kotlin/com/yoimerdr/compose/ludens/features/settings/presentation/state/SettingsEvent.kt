package com.yoimerdr.compose.ludens.features.settings.presentation.state

import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey

/**
 * Events for the settings screen.
 */
sealed interface SettingsEvent {
    /**
     * Selects a settings section.
     *
     * @param section The section to select.
     */
    data class OnSelectSection(val section: SettingsSection) : SettingsEvent

    /**
     * Updates the enabled state of all controls (that can be disabled).
     *
     * @param enabled Whether controls are enabled.
     */
    data class UpdateControlsEnabled(val enabled: Boolean) :
        SettingsEvent

    /**
     * Updates the alpha transparency of all controls.
     *
     * @param alpha The alpha value.
     */
    data class UpdateControlsAlpha(val alpha: Float) :
        SettingsEvent

    /**
     * Updates the enabled state of a specific control.
     *
     * @param type The control type.
     * @param enabled Whether the control is enabled.
     */
    data class UpdateControlEnabled(val type: ItemType, val enabled: Boolean) :
        SettingsEvent

    /**
     * Updates the alpha transparency of a specific control.
     *
     * @param type The control type.
     * @param alpha The alpha value.
     */
    data class UpdateControlAlpha(val type: ItemType, val alpha: Float) :
        SettingsEvent

    /**
     * Updates the key binding for a specific control.
     *
     * @param type The control type.
     * @param key The input key to bind.
     */
    class UpdateControlKey(val type: ItemType, val key: InputKey) :
        SettingsEvent

    /**
     * Resets all settings to default values.
     */
    data object RestoreDefaultSettings : SettingsEvent
}


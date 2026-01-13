package com.yoimerdr.compose.ludens.features.settings.presentation.state

import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.model.settings.PositionableType
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
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
     * Requests to enter or exit control movement mode.
     *
     * @param enabled Whether to enable movement mode.
     */
    data class UpdateControlMovementMode(val enabled: Boolean) :
        SettingsEvent

    /**
     * Updates the mute state of the application.
     *
     * @param enabled Whether audio is muted.
     */
    data class UpdateAudioMuted(val enabled: Boolean) :
        SettingsEvent

    /**
     * Updates the FPS counter visibility.
     *
     * @param enabled Whether the FPS counter is visible.
     */
    data class UpdateShowFps(val enabled: Boolean) :
        SettingsEvent

    /**
     * Changes the application language.
     *
     * @param language The language to apply.
     */
    data class OnChangeLanguage(val language: SystemLanguage) : SettingsEvent

    /**
     * Changes the application theme.
     *
     * @param theme The theme to apply.
     */
    data class OnChangeTheme(val theme: SystemTheme) : SettingsEvent

    /**
     * Updates the position of a control.
     *
     * @param type The control type.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    data class UpdateControlPosition(
        val type: PositionableType,
        val x: Float,
        val y: Float,
    ) : SettingsEvent

    /**
     * Resets all settings to default values.
     */
    data object RestoreDefaultSettings : SettingsEvent
}


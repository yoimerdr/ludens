package com.yoimerdr.compose.ludens.features.settings.presentation.state

import androidx.compose.ui.geometry.Rect
import com.yoimerdr.compose.ludens.app.navigation.Destination
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
     * @param index The control index.
     * @param enabled Whether the control is enabled.
     */
    data class UpdateControlEnabled(val index: Int, val enabled: Boolean) :
        SettingsEvent

    /**
     * Updates the alpha transparency of a specific control.
     *
     * @param index The control index.
     * @param alpha The alpha value.
     */
    data class UpdateControlAlpha(val index: Int, val alpha: Float) :
        SettingsEvent

    /**
     * Updates the key binding for a specific control.
     *
     * @param index The control index.
     * @param key The input key to bind.
     */
    class UpdateControlKey(val index: Int, val key: InputKey) :
        SettingsEvent

    /**
     * Specific event related to navigation within the settings screen.
     * */
    data class NavigateTo(val destination: Destination) : SettingsEvent

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
     * Updates the WebGL state of the application.
     *
     * @param enabled Whether WebGL is enabled.
     * */
    data class UpdateUseWebGL(val enabled: Boolean) : SettingsEvent

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
     * @param index The control index.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    data class UpdateControlPosition(
        val index: Int,
        val x: Float,
        val y: Float,
    ) : SettingsEvent

    /**
     * Swaps the positions of two controls within specified bounds.
     *
     * @property indices A pair of indices representing the two controls to swap.
     * @property bounds The [Rect] boundary within which the controls should remain after swapping.
     */
    data class SwapControlPositions(
        val indices: Pair<Int, Int>,
        val bounds: Rect,
    ) : SettingsEvent

    /**
     * Resets the positions of specific controls to their default values.
     *
     * @property items The set of [PositionableType] to reset to default positions.
     * If constructed with the no-arg constructor, all positionable controls are reset.
     */
    data class RestoreDefaultControlPositions(
        val items: Set<PositionableType>,
    ) : SettingsEvent {
        /**
         * Constructs a [RestoreDefaultControlPositions] event that resets all positionable controls.
         */
        constructor() : this(PositionableType.entries.toSet())
    }

    /**
     * Resets all settings to default values.
     */
    data object RestoreDefaultSettings : SettingsEvent

    /**
     * Resets specific settings categories to their default values.
     *
     * This event allows selective restoration of settings by category.
     * If [categories] is empty, all categories will be reset (equivalent to [RestoreDefaultSettings]).
     * Settings that require confirmation (such as audio mute or WebGL changes) will trigger
     * appropriate confirmation dialogs before the reset is applied.
     *
     * @param categories The set of [SettingsCategory] to reset to defaults.
     *
     * @see SettingsCategory
     * @see RestoreDefaultSettings
     */
    data class RestoreDefaultsByCategory(
        val categories: Set<SettingsCategory>,
    ) : SettingsEvent

    /**
     * A pending settings request is rejected.
     */
    data object RequestRejected : SettingsEvent

    /**
     * A pending settings request is resolved.
     *
     * @param success Whether the request was successfully resolved.
     */
    data class RequestResolved(val success: Boolean) : SettingsEvent
}


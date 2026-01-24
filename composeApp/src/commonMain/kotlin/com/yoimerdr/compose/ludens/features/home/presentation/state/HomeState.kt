package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import ludens.composeapp.generated.resources.Res

/**
 * Represents the UI state of the home screen.
 *
 * @property settings The current application settings state
 * @property plugin The current plugin state
 * @property isLoading Whether the home screen is currently loading
 */
data class HomeState(
    val settings: SettingsState = SettingsState.default,
    val plugin: PluginState = PluginState(),
    val features: WebFeaturesState = WebFeaturesState(),
    val entry: EntryState = EntryState(
        url = Res.getUri(FallbackIndexFilename),
        isAvailable = false
    ),
    val isLoading: Boolean = true,
)
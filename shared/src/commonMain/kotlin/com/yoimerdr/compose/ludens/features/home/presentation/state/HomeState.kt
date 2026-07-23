package com.yoimerdr.compose.ludens.features.home.presentation.state

import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import ludens.composeapp.generated.resources.Res

/**
 * Represents the UI state of the home screen.
 *
 * @property settings The current application settings state
 * @property entry The entry point state for the web view
 * @property isLoading Whether the home screen is currently loading initial data
 */
data class HomeState(
    val settings: SettingsState = SettingsState.default,
    val entry: EntryState = EntryState(
        url = Res.getUri(FallbackIndexFilename),
        isAvailable = false
    ),
    val isLoading: Boolean = true,
)
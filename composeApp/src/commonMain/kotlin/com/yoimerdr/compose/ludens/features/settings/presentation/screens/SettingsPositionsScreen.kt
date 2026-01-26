package com.yoimerdr.compose.ludens.features.settings.presentation.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.features.settings.presentation.section.MovableControlsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ActionSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ControlPositionsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.layout.SafeContent
import org.koin.compose.viewmodel.koinViewModel


/**
 * Screen for repositioning control elements in the settings.
 * Provides an interface for users to drag and adjust the position of controls.
 *
 * @param nav The navigation controller for handling screen navigation.
 * @param viewModel The settings view model for managing settings state and events.
 */
@Composable
fun SettingsPositionsScreen(
    nav: NavController,
    viewModel: ControlPositionsSettingsViewModel = koinViewModel(),
    actionViewModel: ActionSettingsViewModel = koinViewModel(),
) {
    SafeContent {
        MovableControlsSettingsSection(
            positionsViewModel = viewModel,
            actionsViewModel = actionViewModel,
            onCloseClick = {
                nav.popBackStack()
            }
        )
    }
}

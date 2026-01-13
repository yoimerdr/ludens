package com.yoimerdr.compose.ludens.features.settings.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.navigation.navigateTo
import com.yoimerdr.compose.ludens.features.settings.presentation.layout.SettingsContents
import com.yoimerdr.compose.ludens.features.settings.presentation.secction.MovableControlsSettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsMode
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.dialogs.ConfirmationDialog
import com.yoimerdr.compose.ludens.ui.components.dialogs.widthInDialog
import com.yoimerdr.compose.ludens.ui.components.layout.SafeContent
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_text_restarting_confirmation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * The main settings screen displaying configuration options and handling navigation.
 *
 * @param nav The navigation controller for screen navigation.
 * @param features The current web features state.
 * @param plugin The current plugin state.
 * @param viewModel The settings view model.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    nav: NavController,
    features: WebFeaturesState,
    plugin: PluginState,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val mode by viewModel.modeState.collectAsStateWithLifecycle()

    BackHandler(mode !is SettingsMode.Initializing) {
        if (mode is SettingsMode.MovableControls)
            viewModel.onEvent(SettingsEvent.UpdateControlMovementMode(false))
        else if (mode is SettingsMode.PendingMuteConfirmation)
            viewModel.onEvent(SettingsEvent.ResolveMuted(false))
        else if (viewModel.requireRestart)
            nav.navigateTo(Destination.Splash)
        else nav.popBackStack()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ConfirmationDialog(
            message = stringResource(Res.string.stc_text_restarting_confirmation),
            modifier = Modifier
                .widthInDialog(),
            showDialog = mode is SettingsMode.PendingMuteConfirmation,
            onConfirm = {
                viewModel.onEvent(SettingsEvent.ResolveMuted(true))
            },
            onDismiss = {
                viewModel.onEvent(SettingsEvent.ResolveMuted(false))
            }
        )

        when (mode) {
            is SettingsMode.MovableControls -> {
                SafeContent {
                    MovableControlsSettingsSection(
                        viewModel = viewModel
                    )
                }
            }

            else -> {
                SettingsContents(
                    features = features,
                    plugin = plugin,
                    onClose = {
                        if (viewModel.requireRestart)
                            nav.navigateTo(Destination.Splash)
                        else nav.popBackStack()
                    },
                    viewModel = viewModel,
                )
            }
        }
    }
}

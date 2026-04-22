package com.yoimerdr.compose.ludens.features.settings.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.navigation.navigateTo
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.features.settings.presentation.layout.SettingsContents
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateActionEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateUseWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ActionSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ControlsSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.RootSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.ToolsSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.dialogs.ConfirmationDialog
import com.yoimerdr.compose.ludens.ui.components.dialogs.widthInDialog
import com.yoimerdr.compose.ludens.ui.components.interaction.reject
import com.yoimerdr.compose.ludens.ui.components.interaction.resolve
import com.yoimerdr.compose.ludens.ui.components.provider.LocalInteractionManager
import com.yoimerdr.compose.ludens.ui.components.provider.LocalPlugin
import com.yoimerdr.compose.ludens.ui.components.provider.LocalWebFeatures
import kotlinx.coroutines.launch
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.stc_text_restarting_confirmation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * The main settings screen displaying configuration options and handling navigation.
 *
 * @param nav The navigation controller for screen navigation.
 * @param viewModel The settings view model.
 * @param controlsViewModel The controls settings view model.
 * @param toolsViewModel The tools settings view model.
 * @param systemViewModel The system settings view model.
 * @param actionViewModel The action settings view model.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    nav: NavController,
    viewModel: RootSettingsViewModel = koinViewModel(),
    controlsViewModel: ControlsSettingsViewModel = koinViewModel(),
    toolsViewModel: ToolsSettingsViewModel = koinViewModel(),
    systemViewModel: SystemSettingsViewModel = koinViewModel(),
    actionViewModel: ActionSettingsViewModel = koinViewModel(),
    onRestart: () -> Unit = {
        nav.navigateTo(Destination.Splash)
    },
) {
    val interactionManager = LocalInteractionManager.current
    val request by interactionManager.request.collectAsStateWithLifecycle()

    SettingsEffects(
        viewModel,
        actionViewModel,
        onNavigate = { destination ->
            nav.navigateTo(destination)
        },
    )

    BackHandler {
        if (request != null) {
            viewModel.viewModelScope.launch {
                interactionManager.reject()
            }
        } else if (viewModel.requireRestart)
            onRestart()
        else nav.popBackStack()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SettingsContents(
            onClose = {
                if (viewModel.requireRestart)
                    onRestart()
                else nav.popBackStack()
            },
            viewModel = viewModel,
            controlsViewModel = controlsViewModel,
            toolsViewModel = toolsViewModel,
            systemViewModel = systemViewModel,
            actionViewModel = actionViewModel,
        )

        ConfirmationDialog(
            message = stringResource(Res.string.stc_text_restarting_confirmation),
            modifier = Modifier
                .widthInDialog(),
            showDialog = request != null,
            onConfirm = {
                viewModel.viewModelScope.launch {
                    interactionManager.resolve(null)
                }
            },
            onDismiss = {
                viewModel.viewModelScope.launch {
                    interactionManager.reject()
                }
            }
        )
    }
}

@Composable
private fun SettingsEffects(
    viewModel: RootSettingsViewModel,
    actionsViewModel: ActionSettingsViewModel,
    onNavigate: (Destination) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is SettingsEvent.NavigateTo -> onNavigate(it.destination)

                else -> {}
            }
        }
    }

    val plugin = LocalPlugin.current
    val features = LocalWebFeatures.current

    val actions by actionsViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(plugin, actions) {
        // Try to disable items if for some reason they are enabled while the plugin is disabled or the features are not supported
        actions.items.forEachIndexed { index, it ->
            when (it.type) {
                ActionType.ToggleFPS -> {
                    if (it.enabled && !plugin.isEnabled) {
                        actionsViewModel.handle(UpdateActionEnabled(index, false))
                    }
                }

                ActionType.ToggleWebGL -> {
                    if (it.enabled && (!features.supportsWebGL || !plugin.canToggleDrawEngine)) {
                        actionsViewModel.handle(
                            UpdateUseWebGL(false)
                        )
                    }
                }

                else -> {}
            }
        }
    }
}
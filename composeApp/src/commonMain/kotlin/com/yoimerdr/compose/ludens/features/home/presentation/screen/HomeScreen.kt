package com.yoimerdr.compose.ludens.features.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.ui.providers.LocalFPSPlayer
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.features.home.presentation.sections.HomeScreenContent
import com.yoimerdr.compose.ludens.features.home.presentation.sections.WebGame
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeRequest
import com.yoimerdr.compose.ludens.features.home.presentation.state.RequestMute
import com.yoimerdr.compose.ludens.features.home.presentation.state.RequestWebGL
import com.yoimerdr.compose.ludens.features.home.presentation.state.RestartingRequest
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.ui.components.dialogs.ConfirmationDialog
import com.yoimerdr.compose.ludens.ui.components.dialogs.widthInDialog
import com.yoimerdr.compose.ludens.ui.components.interaction.reject
import com.yoimerdr.compose.ludens.ui.components.interaction.resolve
import com.yoimerdr.compose.ludens.ui.components.layout.SafeContent
import com.yoimerdr.compose.ludens.ui.components.provider.CollectInteractionResult
import com.yoimerdr.compose.ludens.ui.components.provider.LocalInteractionManager
import com.yoimerdr.compose.ludens.ui.components.provider.LocalPlugin
import com.yoimerdr.compose.ludens.ui.components.provider.LocalWebFeatures
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.isAvailable
import kotlinx.coroutines.launch
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.hmc_text_restarting_confirmation
import org.jetbrains.compose.resources.stringResource

/**
 * Main screen for the home view displaying the game content and interactive controls.
 *
 * This composable renders the game within a web view and overlays UI controls.
 * It manages the visibility of the FPS counter and game controls, delegating event
 * handling to UI components while coordinating state through events to the ViewModel.
 *
 * @param nav Navigation controller for navigating to other screens (e.g., Settings)
 * @param showControls Whether to show the game controls (joystick, buttons)
 * @param viewModel The home view model managing screen state and event handling.
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 * @param onRestart Optional callback invoked when a restart request is resolved.
 */
@Composable
fun HomeScreen(
    nav: NavController,
    viewModel: HomeViewModel,
    showControls: Boolean = true,
    onLoad: ((PluginState) -> Unit)? = null,
    onRestart: (() -> Unit)? = null,
) {
    HomeScreenEffects(
        viewModel = viewModel,
        nav = nav,
        onRestart = onRestart
    )

    SafeContent(
        fullBackgroundContent = {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black)
            )

            WebGame(
                viewModel = viewModel,
                onLoad = onLoad
            )
        },
        fullForegroundContent = {
            HomeScreenConfirmation(
                viewModel = viewModel,
            )
        }
    ) {
        HomeScreenContent(
            viewModel = viewModel,
            showControls = showControls,
            onConfiguration = {
                nav.navigate(Destination.Settings.route)
            }
        )
    }

}

/**
 * Displays the confirmation dialog for handling interaction requests.
 *
 * This composable listens for interaction requests from the [LocalInteractionManager]
 * and displays a confirmation dialog when a request requires user approval.
 * It handles the resolve and reject actions through the ViewModel.
 *
 * @param viewModel The home view model for handling interaction resolution
 */
@Composable
private fun HomeScreenConfirmation(
    viewModel: HomeViewModel,
) {
    CollectInteractionResult(
        onReject = {
            if (it.request is HomeRequest)
                viewModel.reject(it.request)
        }
    ) {
        if (it.request is HomeRequest)
            viewModel.resolve(it.request)
    }

    val interactor = LocalInteractionManager.current
    val request by interactor.request.collectAsStateWithLifecycle()

    ConfirmationDialog(
        showDialog = request != null,
        modifier = Modifier
            .widthInDialog(),
        message = stringResource(Res.string.hmc_text_restarting_confirmation),
        onConfirm = {
            viewModel.viewModelScope.launch {
                interactor.resolve(true)
            }
        },
        onDismiss = {
            viewModel.viewModelScope.launch {
                interactor.reject()
            }
        }
    )
}

/**
 * Manages side effects for the home screen.
 *
 * This composable handles non-UI logic such as:
 * - Updating entry state based on web features
 * - managing FPS player visibility
 * - Handling home events (navigation, toggles, etc.)
 *
 * @param viewModel The home view model source of events and state
 * @param nav The navigation controller for screen transitions
 * @param onRestart Optional callback to invoke when a restart is requested
 */
@Composable
private fun HomeScreenEffects(
    viewModel: HomeViewModel,
    nav: NavController,
    onRestart: (() -> Unit)? = null,
) {
    val tools by viewModel.toolState.collectAsStateWithLifecycle()
    val isLoading by viewModel.loadingState.collectAsStateWithLifecycle()

    val features = LocalWebFeatures.current

    LaunchedEffect(isLoading, features) {
        println("HomeScreenEffects: isLoading=$isLoading, features=$features")
        viewModel.handle(HomeEvent.UpdateEntry(features))
    }

    val player = LocalFPSPlayer.current
    val plugin = LocalPlugin.current
    LaunchedEffect(isLoading, plugin.isAvailable, tools.showFPS) {
        if (plugin.isAvailable) {
            if (tools.showFPS) {
                player.show()
            } else player.hide()
        }
    }

    val interactionManager = LocalInteractionManager.current

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is HomeEvent.OnClickAction -> {
                    when (it.action.type) {
                        ActionType.ToggleMute -> {
                            interactionManager.request(RequestMute(!tools.isMuted))
                        }

                        ActionType.ToggleWebGL -> {
                            interactionManager.request(RequestWebGL(!tools.useWebGL))
                        }

                        ActionType.Settings -> {
                            nav.navigate(Destination.Settings.route)
                        }

                        ActionType.ToggleFPS -> {
                            viewModel.handle(HomeEvent.ToggleFpsCounter)
                        }

                        ActionType.ToggleControls -> {
                            viewModel.handle(HomeEvent.ToggleControlsVisibility)
                        }

                        else -> {}
                    }
                }

                is HomeEvent.ResolvedRequest -> {
                    if (it.request is RestartingRequest)
                        onRestart?.invoke()
                }

                else -> {}
            }
        }
    }
}

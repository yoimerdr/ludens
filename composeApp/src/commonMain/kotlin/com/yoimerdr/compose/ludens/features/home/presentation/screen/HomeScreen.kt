package com.yoimerdr.compose.ludens.features.home.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.navigation.navigateTo
import com.yoimerdr.compose.ludens.app.ui.providers.LocalFPSPlayer
import com.yoimerdr.compose.ludens.features.home.presentation.components.WebGame
import com.yoimerdr.compose.ludens.features.home.presentation.sections.HomeScreenContent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.state.rememberEntryState
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.ui.components.layout.SafeContent
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main screen for the home view displaying the game content and interactive controls.
 *
 * Controls are only displayed when the `YPD_Ludens` plugin has finished loading and the entry
 * file is available. The FPS counter visibility is automatically synced with settings.
 *
 * @param nav Navigation controller for navigating to other screens (e.g., Settings)
 * @param features Web features state to configure web view capabilities
 * Defaults to a new remembered graphics layer.
 * @param viewModel The home view model managing screen state and event handling.
 * Defaults to a Koin-injected instance.
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 */
@Composable
fun HomeScreen(
    nav: NavController,
    features: WebFeaturesState,
    viewModel: HomeViewModel = koinViewModel(),
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val isLoading by viewModel.loadingState.collectAsStateWithLifecycle()
    val tools by viewModel.toolState.collectAsStateWithLifecycle()

    val counter = LocalFPSPlayer.current
    LaunchedEffect(isLoading, tools.showFPS) {
        viewModel.onEvent(HomeEvent.ToggleFpsCounter(counter))
    }

    val entry = rememberEntryState(
        features = features,
        isLoading = isLoading,
        settings = tools,
    )

    SafeContent(
        fullBackgroundContent = {
            WebGame(
                fileUrl = entry.url,
            ) {
                onLoad?.invoke(it)
                viewModel.onEvent(HomeEvent.OnPluginLoaded(it))
            }
        }
    ) {
        if (entry.isAvailable) {
            HomeScreenContent(
                viewModel = viewModel,
                onConfiguration = {
                    nav.navigateTo(Destination.Settings)
                }
            )
        }
    }
}


package com.yoimerdr.compose.ludens.features.home.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
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
 * This screen composes several key components:
 * - A web view that loads and displays the HTML5 game
 * - Game control overlay (joystick, action buttons, configuration button)
 * - FPS counter management
 * - Plugin loading detection and state management
 *
 * Controls are only displayed when the `YPD_Ludens` plugin has finished loading and the entry
 * file is available. The FPS counter visibility is automatically synced with settings.
 *
 * @param nav Navigation controller for navigating to other screens (e.g., Settings)
 * @param features Web features state to configure web view capabilities
 * @param webGraphicsLayer Graphics layer for record the web view content.
 * Defaults to a new remembered graphics layer.
 * @param viewModel The home view model managing screen state and event handling.
 * Defaults to a Koin-injected instance.
 * @param showControls Whether to show the game controls (joystick, buttons)
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 */
@Composable
fun HomeScreen(
    nav: NavController,
    features: WebFeaturesState,
    webGraphicsLayer: GraphicsLayer = rememberGraphicsLayer(),
    viewModel: HomeViewModel = koinViewModel(),
    showControls: Boolean = true,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    // settings state
    val state by viewModel.state.collectAsStateWithLifecycle()

    val settings = state.settings
    val tools = settings.tool
    val plugin = state.plugin

    val counter = LocalFPSPlayer.current
    LaunchedEffect(plugin.isLoading, tools.showFPS) {
        viewModel.onEvent(HomeEvent.ToggleFpsCounter(counter))
    }

    val entry = rememberEntryState(
        features = features,
        state = state,
    )

    SafeContent(
        fullBackgroundContent = {
            WebGame(
                modifier = Modifier.drawWithContent {
                    webGraphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(webGraphicsLayer)
                },
                fileUrl = entry.url,
            ) {
                onLoad?.invoke(it)
                viewModel.onEvent(HomeEvent.OnPluginLoaded(it))
            }
        }
    ) {
        if (!plugin.isLoading && entry.isAvailable) {
            HomeScreenContent(
                controls = settings.control,
                positions = settings.control.positions,
                onEvent = viewModel::onEvent,
                onConfiguration = {
                    nav.navigateTo(Destination.Settings)
                },
                showControls = showControls
            )
        }
    }
}


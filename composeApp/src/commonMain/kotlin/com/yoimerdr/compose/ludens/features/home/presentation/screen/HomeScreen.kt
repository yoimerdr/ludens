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
import androidx.navigation.NavController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.ui.providers.LocalFPSPlayer
import com.yoimerdr.compose.ludens.features.home.presentation.components.WebGame
import com.yoimerdr.compose.ludens.features.home.presentation.sections.HomeScreenContent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.ui.components.layout.SafeContent
import com.yoimerdr.compose.ludens.ui.components.provider.LocalPlugin
import com.yoimerdr.compose.ludens.ui.state.PluginState

/**
 * Main screen for the home view displaying the game content and interactive controls.
 *
 * Controls are only displayed when the `YPD_Ludens` plugin has finished loading and the entry
 * file is available. The FPS counter visibility is automatically synced with settings.
 *
 * @param nav Navigation controller for navigating to other screens (e.g., Settings)
 * @param showControls Whether to show the game controls (joystick, buttons)
 * @param viewModel The home view model managing screen state and event handling.
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 */
@Composable
fun HomeScreen(
    nav: NavController,
    viewModel: HomeViewModel,
    showControls: Boolean = true,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val counter = LocalFPSPlayer.current
    val plugin = LocalPlugin.current

    val isLoading by viewModel.loadingState.collectAsStateWithLifecycle()
    val tools by viewModel.toolState.collectAsStateWithLifecycle()
    val entry by viewModel.entryState.collectAsStateWithLifecycle()

    LaunchedEffect(isLoading, tools.showFPS) {
        viewModel.handle(HomeEvent.ToggleFpsCounter(counter))
    }

    SafeContent(
        fullBackgroundContent = {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black)
            )

            WebGame(
                fileUrl = entry.url,
            ) {
                onLoad?.invoke(it)
                viewModel.handle(HomeEvent.OnPluginLoaded(it))
            }
        }
    ) {
        if (!plugin.isLoading && entry.isAvailable) {
            HomeScreenContent(
                viewModel = viewModel,
                onConfiguration = {
                    nav.navigate(Destination.Settings.route)
                },
                showControls = showControls
            )
        }
    }
}


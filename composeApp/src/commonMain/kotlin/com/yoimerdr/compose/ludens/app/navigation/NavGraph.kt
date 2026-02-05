package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yoimerdr.compose.ludens.app.ui.screens.SplashScreen
import com.yoimerdr.compose.ludens.features.home.presentation.screen.HomeScreen
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.features.settings.presentation.screens.SettingsPositionsScreen
import com.yoimerdr.compose.ludens.features.settings.presentation.screens.SettingsScreen
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SystemSettingsViewModel
import com.yoimerdr.compose.ludens.ui.components.provider.ProvideInteractionManager
import com.yoimerdr.compose.ludens.ui.components.provider.ProvideWebPlugin
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.PluginStateSaver
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesStateSaver
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    systemViewModel: SystemSettingsViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    var plugin by rememberSaveable(stateSaver = PluginStateSaver) {
        mutableStateOf(PluginState())
    }

    var features by rememberSaveable(stateSaver = WebFeaturesStateSaver) {
        mutableStateOf(WebFeaturesState())
    }

    val onRestart = {
        plugin = PluginState()
        features = WebFeaturesState()
        navController.navigateTo(Destination.Splash)
    }

    ProvideWebPlugin(
        plugin = plugin,
        features = features,
    ) {
        NavHomeGraph(
            nav = navController,
            viewModel = homeViewModel,
            onLoad = {
                plugin = it
            },
            onRestart = onRestart
        )

        NavHost(
            navController = navController,
            startDestination = Destination.Splash.route,
        ) {
            composable(
                route = Destination.Splash.route,
                arguments = Destination.Splash.arguments,
            ) {
                SplashScreen(navController) {
                    features = it
                }
            }

            composable(
                route = Destination.Home.route,
                arguments = Destination.Home.arguments,
            ) {}

            composable(
                route = Destination.Settings.route,
                arguments = Destination.Settings.arguments,
            ) {
                ProvideInteractionManager {
                    SettingsScreen(
                        nav = navController,
                        systemViewModel = systemViewModel,
                        onRestart = onRestart
                    )
                }
            }

            composable(
                route = Destination.SettingsPositions.route,
                arguments = Destination.SettingsPositions.arguments
            ) {
                SettingsPositionsScreen(
                    nav = navController,
                )
            }
        }
    }
}


@Composable
private fun NavHomeGraph(
    nav: NavHostController,
    viewModel: HomeViewModel,
    onLoad: ((PluginState) -> Unit)? = null,
    onRestart: (() -> Unit)? = null,
) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route
    val show = currentRoute != null && currentRoute != Destination.Splash.route

    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ProvideInteractionManager {
            HomeScreen(
                nav = nav,
                onLoad = onLoad,
                viewModel = viewModel,
                showControls = currentRoute?.contains(Destination.Settings.route) == false,
                onRestart = onRestart
            )
        }
    }
}

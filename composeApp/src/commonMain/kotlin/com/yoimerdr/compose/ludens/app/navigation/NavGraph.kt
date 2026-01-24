package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yoimerdr.compose.ludens.app.ui.screens.SplashScreen
import com.yoimerdr.compose.ludens.features.home.presentation.screen.HomeScreen
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.ui.components.provider.ProvideWebPlugin
import com.yoimerdr.compose.ludens.ui.state.PluginState
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val plugin by homeViewModel.pluginState.collectAsStateWithLifecycle()
    val features by homeViewModel.featuresState.collectAsStateWithLifecycle()

    ProvideWebPlugin(
        plugin = plugin,
        features = features,
    ) {
        NavHomeGraph(
            nav = navController,
            viewModel = homeViewModel,
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
                    homeViewModel.handle(HomeEvent.OnWebFeaturesLoaded(it))
                }
            }

            composable(
                route = Destination.Home.route,
                arguments = Destination.Home.arguments,
            ) {}

            composable(
                route = Destination.Settings.route,
                arguments = Destination.Settings.arguments,
            ) {}
        }
    }
}


@Composable
private fun NavHomeGraph(
    nav: NavHostController,
    viewModel: HomeViewModel,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route
    val show = currentRoute != null && currentRoute != Destination.Splash.route

    AnimatedVisibility(
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        HomeScreen(
            nav = nav,
            onLoad = onLoad,
            viewModel = viewModel,
            showControls = currentRoute?.contains(Destination.Settings.route) == false
        )
    }
}

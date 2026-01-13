package com.yoimerdr.compose.ludens.app.navigation

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
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.PluginStateSaver
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesStateSaver

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    var features by rememberSaveable(stateSaver = WebFeaturesStateSaver) {
        mutableStateOf(WebFeaturesState())
    }

    var plugin by rememberSaveable(stateSaver = PluginStateSaver) {
        mutableStateOf(PluginState())
    }

    NavHomeGraph(
        nav = navController,
        features = features,
    ) {
        plugin = it
    }


    NavHost(
        navController = navController,
        startDestination = Destination.Splash.route,
    ) {
        composable(
            route = Destination.Splash.route, arguments = Destination.Splash.arguments
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
        ) {}
    }

}


@Composable
private fun NavHomeGraph(
    nav: NavHostController,
    features: WebFeaturesState,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route
    val show = currentRoute != null && currentRoute != Destination.Splash.route
    if (!show) return

    HomeScreen(
        nav = nav,
        features = features,
        onLoad = onLoad,
    )
}

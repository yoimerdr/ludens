package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yoimerdr.compose.ludens.app.ui.screens.SplashScreen
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesStateSaver

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val features = rememberSaveable(stateSaver = WebFeaturesStateSaver) {
        mutableStateOf(WebFeaturesState())
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Splash.route,
    ) {
        composable(
            route = Destination.Splash.route,
            arguments = Destination.Splash.arguments
        ) {
            SplashScreen(navController) {
                features.value = it
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

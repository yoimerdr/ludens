package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yoimerdr.compose.ludens.app.ui.screens.SplashScreen
import com.yoimerdr.compose.ludens.features.settings.presentation.screens.SettingsPositionsScreen
import com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel.SettingsViewModel
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesStateSaver
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = koinViewModel(),
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
        ) {

        }

        composable(
            route = Destination.SettingsPositions.route,
            arguments = Destination.SettingsPositions.arguments
        ) {
            SettingsPositionsScreen(
                nav = navController,
                viewModel = settingsViewModel
            )
        }
    }

}

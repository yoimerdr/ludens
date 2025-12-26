package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Splash.route,
    ) {
        composable(
            route = Destination.Splash.route,
            arguments = Destination.Splash.arguments
        ) {

        }

        composable(
            route = Destination.Home.route,
            arguments = Destination.Home.arguments,
        ) {
        }

        composable(
            route = Destination.Settings.route,
            arguments = Destination.Settings.arguments,
        ) {

        }
    }

}



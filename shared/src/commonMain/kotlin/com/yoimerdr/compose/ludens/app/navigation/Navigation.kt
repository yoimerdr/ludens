package com.yoimerdr.compose.ludens.app.navigation

import androidx.annotation.MainThread
import androidx.navigation.NavController

@MainThread
fun NavController.navigateTo(destination: Destination) = navigate(destination.route) {
    launchSingleTop = true
    restoreState = false

    popUpTo(graph.startDestinationId) {
        inclusive = true
    }
}
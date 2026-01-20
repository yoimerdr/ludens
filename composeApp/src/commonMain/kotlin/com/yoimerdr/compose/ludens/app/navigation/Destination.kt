package com.yoimerdr.compose.ludens.app.navigation

import androidx.navigation.NamedNavArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    object Splash : Destination("splash")
    object Home : Destination("home")
    object Settings : Destination("settings")
    object SettingsPositions : Destination("settings/positions")
}
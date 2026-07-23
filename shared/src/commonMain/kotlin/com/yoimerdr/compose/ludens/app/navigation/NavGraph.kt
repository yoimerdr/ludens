package com.yoimerdr.compose.ludens.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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


/**
 * Builds the application navigation graph and coordinates shared state between screens.
 *
 * The graph keeps the splash screen, home screen, and settings flows in sync while preserving
 * the current plugin and web feature state across navigation changes.
 *
 * @param navController The navigation controller used for all app routes.
 * @param systemViewModel View model used by the settings flow.
 * @param homeViewModel View model used by the home flow.
 */
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
                enterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.985f) + slideInVertically { it / 14 }
                },
                exitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.985f) + slideOutVertically { -it / 14 }
                },
                popEnterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.985f) + slideInVertically { -it / 14 }
                },
                popExitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.985f) + slideOutVertically { it / 14 }
                },
            ) {
                SplashScreen(navController) {
                    features = it
                }
            }

            composable(
                route = Destination.Home.route,
                arguments = Destination.Home.arguments,
                enterTransition = {
                    fadeIn()
                },
                exitTransition = {
                    fadeOut()
                },
                popEnterTransition = {
                    fadeIn()
                },
                popExitTransition = {
                    fadeOut()
                },
            ) {}

            composable(
                route = Destination.Settings.route,
                arguments = Destination.Settings.arguments,
                enterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.965f) + slideInVertically { it / 5 }
                },
                exitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.965f) + slideOutVertically { -it / 6 }
                },
                popEnterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.965f) + slideInVertically { -it / 5 }
                },
                popExitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.965f) + slideOutVertically { it / 6 }
                },
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
                arguments = Destination.SettingsPositions.arguments,
                enterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.975f) + slideInVertically { it / 7 }
                },
                exitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.975f) + slideOutVertically { -it / 8 }
                },
                popEnterTransition = {
                    fadeIn() + scaleIn(initialScale = 0.975f) + slideInVertically { -it / 7 }
                },
                popExitTransition = {
                    fadeOut() + scaleOut(targetScale = 0.975f) + slideOutVertically { it / 8 }
                },
            ) {
                SettingsPositionsScreen(
                    nav = navController,
                )
            }
        }
    }
}


/**
 * Wraps the home screen in a visibility transition that keeps it out of the splash route.
 *
 * The home content is only shown once navigation has moved away from splash, and it reuses the
 * same interaction provider used by the settings flow.
 *
 * @param nav The navigation controller used to inspect the current route.
 * @param viewModel The home screen view model.
 * @param onLoad Optional callback invoked when the home screen finishes loading plugin state.
 * @param onRestart Optional callback invoked when the user requests a full restart.
 */
@Composable
private fun NavHomeGraph(
    nav: NavHostController,
    viewModel: HomeViewModel,
    onLoad: ((PluginState) -> Unit)? = null,
    onRestart: (() -> Unit)? = null,
) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route
    val show = currentRoute != null && currentRoute != Destination.Splash.route
    val justEnteredFromSplash = currentRoute == Destination.Home.route

    AnimatedVisibility(
        visible = show,
        enter = if (justEnteredFromSplash) {
            fadeIn()
        } else {
            fadeIn() + scaleIn(initialScale = 0.995f)
        },
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

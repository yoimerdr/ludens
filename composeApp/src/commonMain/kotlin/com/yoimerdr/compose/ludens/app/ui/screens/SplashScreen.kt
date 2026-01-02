package com.yoimerdr.compose.ludens.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavHostController
import com.yoimerdr.compose.ludens.app.navigation.Destination
import com.yoimerdr.compose.ludens.app.navigation.navigateTo
import com.yoimerdr.compose.ludens.ui.components.splash.DesignSplash
import com.yoimerdr.compose.ludens.ui.components.webview.WebViewStartBoot
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.brand.Ludens
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import kotlinx.coroutines.delay

/**
 * Primary gradient color for the splash screen.
 */
val SplashPrimaryColor = Color(0xFF67D4F9)

/**
 * Secondary gradient color for the splash screen.
 */
val SplashSecondaryColor = Color(0xFFE6B4FF)

/**
 * Displays the application splash screen with loading animation and WebView initialization.
 *
 * This composable shows a branded splash screen with a vertical gradient background while
 * initializing the WebView and its features. Once both the minimum display delay and WebView
 * initialization are complete, it navigates to the Home screen.
 *
 * The splash screen performs two parallel operations:
 * 1. Displays the splash animation for the specified delay duration
 * 2. Initializes the WebView and loads its features
 *
 * Navigation to the Home screen only occurs when both operations are complete.
 *
 * @param nav The [NavHostController] used for navigating to the Home screen after loading.
 * @param delay The minimum duration in milliseconds to display the splash screen. Default is 1500ms.
 * @param onLoad Optional callback invoked with [WebFeaturesState] when WebView initialization is complete,
 * before navigation occurs.
 */
@Composable
fun SplashScreen(
    nav: NavHostController,
    delay: Long = 1500,
    onLoad: ((WebFeaturesState) -> Unit)? = null,
) {
    var params by remember { mutableStateOf<WebFeaturesState?>(null) }
    var isEnd by remember { mutableStateOf(false) }

    LaunchedEffect(onLoad) {
        if (!isEnd) {
            delay(delay)
            isEnd = true
        }
    }

    LaunchedEffect(isEnd, params) {
        if (isEnd && params != null) {
            onLoad?.invoke(params!!)
            nav.navigateTo(Destination.Home)
        }
    }


    SplashContent {
        params = it
    }
}

/**
 * Renders the splash screen content with gradient background and branding.
 *
 * This private composable displays a vertical gradient background from [SplashPrimaryColor]
 * to [SplashSecondaryColor], overlaid with the Ludens logo animation. It also triggers
 * WebView initialization in the background.
 *
 * @param onLoad Callback invoked with [WebFeaturesState] when WebView initialization completes.
 */
@Composable
private fun SplashContent(onLoad: ((WebFeaturesState) -> Unit)) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        SplashPrimaryColor,
                        SplashSecondaryColor,
                    )
                )
            )
    ) {
        DesignSplash(
            imagePainter = rememberVectorPainter(LudensIcons.Brand.Ludens),
            contentDescription = "Ludens Logo",
            duration = 1500
        )
    }

    WebViewStartBoot(onLoad)
}
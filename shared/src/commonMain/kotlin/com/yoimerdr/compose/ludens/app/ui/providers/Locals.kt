package com.yoimerdr.compose.ludens.app.ui.providers

import androidx.compose.runtime.staticCompositionLocalOf
import com.multiplatform.webview.web.WebViewNavigator
import com.yoimerdr.compose.ludens.core.domain.port.player.AudioPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.GraphicPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer

/**
 * Composition local for [WebViewNavigator].
 */
val LocalWebViewNavigator = staticCompositionLocalOf<WebViewNavigator> {
    error("WebViewNavigator not provided")
}

/**
 * Composition local for [InputPlayer].
 */
val LocalInputPlayer = staticCompositionLocalOf<InputPlayer> {
    error("Actions not provided")
}

/**
 * Composition local for [AudioPlayer].
 */
val LocalAudioPlayer = staticCompositionLocalOf<AudioPlayer> {
    error("Audio not provided")
}

/**
 * Composition local for [MovementsPlayer].
 */
val LocalMovementsPlayer = staticCompositionLocalOf<MovementsPlayer> {
    error("Movements not provided")
}

/**
 * Composition local for [FPSPlayer].
 */
val LocalFPSPlayer = staticCompositionLocalOf<FPSPlayer> {
    error("FpsCounter not provided")
}

/**
 * Composition local for [GraphicPlayer].
 */
val LocalGraphicPlayer = staticCompositionLocalOf<GraphicPlayer> {
    error("GraphicsActions not provided")
}
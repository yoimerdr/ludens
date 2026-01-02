package com.yoimerdr.compose.ludens.app.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.yoimerdr.compose.ludens.core.domain.port.player.AudioPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.GraphicPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.presentation.player.rememberJavascriptEvaluator
import com.yoimerdr.compose.ludens.core.presentation.player.rememberKeyEvaluator
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

/**
 * Provides the local dependencies for the application.
 *
 * This composable function is responsible for creating and providing the necessary dependencies
 * for the application, such as the web view navigator, javascript evaluators, and player controllers.
 *
 * @param content The content to be displayed within the scope of the providers.
 */
@Composable
fun LocalProviders(
    content: @Composable () -> Unit,
) {
    val navigator = rememberWebViewNavigator()
    val evaluator =
        rememberJavascriptEvaluator(
            navigator
        )
    val keyEvaluator =
        rememberKeyEvaluator(
            navigator,
            evaluator = evaluator
        )

    val input: InputPlayer = koinInject { parametersOf(keyEvaluator) }
    val audio: AudioPlayer = koinInject { parametersOf(evaluator) }
    val fps: FPSPlayer = koinInject { parametersOf(evaluator) }
    val graphic: GraphicPlayer = koinInject { parametersOf(keyEvaluator) }
    val movements: MovementsPlayer = koinInject { parametersOf(keyEvaluator) }

    CompositionLocalProvider(
        LocalWebViewNavigator provides navigator,
        LocalInputPlayer provides input,
        LocalAudioPlayer provides audio,
        LocalMovementsPlayer provides movements,
        LocalFPSPlayer provides fps,
        LocalGraphicPlayer provides graphic,
        content = content
    )
}
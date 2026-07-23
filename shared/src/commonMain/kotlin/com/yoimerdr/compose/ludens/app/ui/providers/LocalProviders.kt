package com.yoimerdr.compose.ludens.app.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.player.AudioPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.FPSPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.GraphicPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.MovementsPlayer
import com.yoimerdr.compose.ludens.core.presentation.platform.PlatformLocale
import com.yoimerdr.compose.ludens.core.presentation.player.rememberJavascriptEvaluator
import com.yoimerdr.compose.ludens.core.presentation.player.rememberKeyEvaluator
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

/**
 * Provides the local dependencies for the application.
 *
 * This composable function is responsible for creating and providing the necessary dependencies
 * for the application, such as the web view navigator, javascript evaluators, player controllers,
 * and platform-specific locale configuration.
 *
 * @param language The [SystemLanguage] to apply for localization. If `null`, uses the system default.
 * Defaults to `null`.
 * @param platform The [PlatformLocale] instance to use for locale management. Injected via Koin.
 * @param navigator The [WebViewNavigator] for controlling web view navigation. Defaults to
 * a remembered instance via [rememberWebViewNavigator].
 * @param evaluator The [ScriptEvaluator] for executing JavaScript code. Defaults to a
 * remembered instance via [rememberJavascriptEvaluator] using the provided navigator.
 * @param keyEvaluator The [KeyEventEvaluator] for handling keyboard events. Defaults to a
 * remembered instance via [rememberKeyEvaluator] using the navigator and evaluator.
 * @param inputPlayer The [InputPlayer] controller for managing input operations. Injected via
 * Koin with the keyEvaluator as a parameter.
 * @param audioPlayer The [AudioPlayer] controller for managing audio playback. Injected via
 * Koin with the evaluator as a parameter.
 * @param fpsPlayer The [FPSPlayer] controller for managing frames per second. Injected via
 * Koin with the evaluator as a parameter.
 * @param graphicPlayer The [GraphicPlayer] controller for managing graphics rendering. Injected
 * via Koin with the keyEvaluator as a parameter.
 * @param movementsPlayer The [MovementsPlayer] controller for managing character movements.
 * Injected via Koin with the keyEvaluator as a parameter.
 * @param content The content to be displayed within the scope of the providers.
 */
@Composable
fun LocalProviders(
    language: SystemLanguage? = null,
    platform: PlatformLocale = koinInject(),
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    evaluator: ScriptEvaluator = rememberJavascriptEvaluator(navigator),
    keyEvaluator: KeyEventEvaluator = rememberKeyEvaluator(navigator, evaluator = evaluator),
    inputPlayer: InputPlayer = koinInject { parametersOf(keyEvaluator) },
    audioPlayer: AudioPlayer = koinInject { parametersOf(evaluator) },
    fpsPlayer: FPSPlayer = koinInject { parametersOf(evaluator) },
    graphicPlayer: GraphicPlayer = koinInject { parametersOf(keyEvaluator) },
    movementsPlayer: MovementsPlayer = koinInject { parametersOf(keyEvaluator) },
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalWebViewNavigator provides navigator,
        LocalInputPlayer provides inputPlayer,
        LocalAudioPlayer provides audioPlayer,
        LocalMovementsPlayer provides movementsPlayer,
        LocalFPSPlayer provides fpsPlayer,
        LocalGraphicPlayer provides graphicPlayer,
        platform provides language,
        content = content
    )
}

/**
 * Provides a platform-specific locale context to the composition.
 *
 * This composable wraps the content with a localized context based on the specified
 * language, allowing child composables to access localized resources and content.
 *
 * @param language The [SystemLanguage] to apply, or null to use the system default.
 * @param platform The [PlatformLocale] instance to use for locale management.
 * @param content The composable content that will have access to the localized context.
 */
@Composable
fun ProvidesPlatformLocal(
    language: SystemLanguage?,
    platform: PlatformLocale = koinInject(),
    content: @Composable () -> Unit,
) {
    platform.ProvidesLocale(value = language, content = content)
}
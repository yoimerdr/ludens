package com.yoimerdr.compose.ludens.app.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
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
 * @param language The [SystemLanguage] to apply for localization, or null to use the system default.
 * @param platform The [PlatformLocale] instance to use for locale management.
 * @param content The content to be displayed within the scope of the providers.
 */
@Composable
fun LocalProviders(
    language: SystemLanguage? = null,
    platform: PlatformLocale = koinInject(),
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
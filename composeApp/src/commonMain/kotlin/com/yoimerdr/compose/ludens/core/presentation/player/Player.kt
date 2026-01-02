package com.yoimerdr.compose.ludens.core.presentation.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.multiplatform.webview.web.WebViewNavigator
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.JavascriptEvaluatorAdapter
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.KeyEventEvaluatorAdapter
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventScriptBuilder
import org.koin.compose.koinInject

/**
 * Creates and remembers a JavaScript evaluator for the given WebView navigator.
 *
 * This composable function creates a [ScriptEvaluator] that uses the WebView's
 * JavaScript evaluation capability. The evaluator is remembered and tied to the
 * navigator's lifecycle.
 *
 * @param navigator The [WebViewNavigator] used to execute JavaScript code in the WebView.
 * @return A [ScriptEvaluator] instance that evaluates JavaScript in the WebView.
 */
@Composable
fun rememberJavascriptEvaluator(
    navigator: WebViewNavigator,
): ScriptEvaluator {
    return remember(navigator) {
        JavascriptEvaluatorAdapter { script, callback ->
            navigator.evaluateJavaScript(script, callback)
        }
    }
}

/**
 * Creates and remembers a key event evaluator for the given WebView navigator.
 *
 * This composable function creates a [KeyEventEvaluator] that can generate and
 * evaluate key event scripts in the WebView. The evaluator is remembered and
 * tied to the timeout and evaluator parameters.
 *
 * @param navigator The [WebViewNavigator] used to execute JavaScript code in the WebView.
 * @param timeout The timeout in milliseconds for automatic key release events. Default is 150ms.
 * @param evaluator The [ScriptEvaluator] to use. Defaults to a JavaScript evaluator for the navigator.
 * @param builder The [KeyEventScriptBuilder] to use for building key event scripts. Injected via Koin.
 * @return A [KeyEventEvaluator] instance that can prepare and evaluate key events.
 */
@Composable
fun rememberKeyEvaluator(
    navigator: WebViewNavigator,
    timeout: Int = 150,
    evaluator: ScriptEvaluator = rememberJavascriptEvaluator(
        navigator
    ),
    builder: KeyEventScriptBuilder = koinInject(),
): KeyEventEvaluator {
    return remember(timeout, evaluator) {
        KeyEventEvaluatorAdapter(
            evaluator, timeout, builder
        )
    }
}

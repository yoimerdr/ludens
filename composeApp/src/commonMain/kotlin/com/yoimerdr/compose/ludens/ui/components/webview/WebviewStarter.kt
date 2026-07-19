package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import ludens.composeapp.generated.resources.Res


/**
 * Monitors the WebView loading state and triggers a coroutine callback once loading finishes
 * successfully with a valid, non-blank loaded URL.
 *
 * @param state The WebView state containing the loading state and last loaded URL to monitor.
 * @param onStart Coroutine callback invoked once loading successfully finishes and a valid URL is present.
 */
@Composable
fun EvaluateOnStart(
    state: WebViewState,
    onStart: suspend CoroutineScope.() -> Unit,
) {
    LaunchedEffect(state.loadingState) {
        if (state.loadingState is LoadingState.Finished && state.lastLoadedUrl.isNullOrEmpty()
                .not()
        ) {
            onStart()
        }
    }
}

/**
 * Asynchronously loads a script file from Compose resources and executes a callback with its content
 * once the WebView successfully finishes loading with a valid, non-blank URL.
 *
 * @param filepath The relative resource path of the script file.
 * @param state The WebView state containing the loading state and last loaded URL to monitor.
 * @param onStart Callback invoked with the decoded script content once loading successfully completes.
 */
@Composable
fun EvaluateScriptOnStart(
    filepath: String,
    state: WebViewState,
    onStart: suspend CoroutineScope.(String) -> Unit,
) {
    var script by remember(filepath) { mutableStateOf<String?>(null) }

    LaunchedEffect(filepath) {
        script = Res.readBytes(filepath).decodeToString()
    }

    EvaluateOnStart(state) {
        val currentScript = script ?: snapshotFlow { script }
            .filterNotNull()
            .first()

        onStart(currentScript)
    }
}

/**
 * Asynchronously loads a script file from Compose resources and automatically evaluates it in the WebView
 * once loading successfully finishes with a valid, non-blank URL.
 *
 * @param filepath The relative resource path of the script file.
 * @param state The WebView state containing the loading state and last loaded URL to monitor.
 * @param navigator The navigator used to evaluate the loaded script.
 */
@Composable
fun EvaluateScriptOnStart(
    filepath: String,
    state: WebViewState,
    navigator: WebViewNavigator,
) {
    EvaluateScriptOnStart(filepath, state) {
        navigator.evaluateJavaScript(it)
    }
}
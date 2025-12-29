package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.zIndex
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewFileReadType
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLFile
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import kotlinx.serialization.json.Json
import ludens.composeapp.generated.resources.Res

private const val StartBootFile = "files/boot/index.html"

/**
 * A JsMessageHandler for handling the start boot event.
 */
private object StartBootHandler : IJsMessageHandler {
    var onLoad: ((WebFeaturesState) -> Unit)? = null
    override fun methodName(): String = "StartBoot"

    override fun handle(
        message: JsMessage,
        navigator: WebViewNavigator?,
        callback: (String) -> Unit,
    ) {
        val it = Json.decodeFromString<WebFeaturesState>(
            message.params,
        )

        onLoad?.invoke(it)
    }
}

/**
 * Setups the [StartBootHandler] with the given onLoad callback.
 *
 * @param onLoad The callback to be invoked when the start boot event is received.
 * @return The [StartBootHandler] instance.
 */
private fun setupStartBootHandler(
    onLoad: (WebFeaturesState) -> Unit,
): IJsMessageHandler {
    StartBootHandler.onLoad =
        onLoad
    return StartBootHandler
}

/**
 * A composable that sets up the javascript bridge for the start boot event.
 *
 * @param state The state of the web view.
 * @param bridge The javascript bridge for the web view.
 * @param onLoad The callback to be invoked when the start boot event is received.
 */
@Composable
private fun StartBridge(
    state: WebViewState,
    bridge: WebViewJsBridge,
    onLoad: ((WebFeaturesState) -> Unit),
) {
    val starter =
        setupStartBootHandler(
            onLoad = onLoad
        )

    LaunchedEffect(bridge) {
        bridge.register(starter)
    }

    LaunchedEffect(state.loadingState) {
        if (state.loadingState is LoadingState.Finished && state.lastLoadedUrl.isNullOrEmpty()
                .not()
        ) {
            if (state.lastLoadedUrl!!.endsWith(StartBootFile)) bridge.webView?.evaluateJavaScript(
                "LudensStartBoot();"
            )
        }
    }
}

/**
 * A hidden web view composable that handles the start boot event.
 *
 * @param onLoad The callback to be invoked when the start boot event is received.
 */
@Composable
fun WebViewStartBoot(onLoad: ((features: WebFeaturesState) -> Unit)) {
    val state = rememberWebViewStateWithHTMLFile(
        Res.getUri(StartBootFile),
        WebViewFileReadType.COMPOSE_RESOURCE_FILES
    )

    state.setup()

    val bridge = rememberWebViewJsBridge()

    StartBridge(
        state,
        bridge,
        onLoad = onLoad
    )

    WebView(
        state = state,
        webViewJsBridge = bridge,
        modifier = Modifier.alpha(0f)
            .zIndex(-1f)
    )
}
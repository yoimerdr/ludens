package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.WebViewState

/**
 * Remembers a [WebViewJsBridge] instance with the name "LudensBridge".
 *
 * @return A [WebViewJsBridge] instance.
 */
@Composable
fun rememberWebViewJsBridge(): WebViewJsBridge {
    return remember {
        WebViewJsBridge(
            jsBridgeName = "LudensBridge"
        )
    }
}

/**
 * Sets up the [WebViewState] with the necessary settings for the application.
 *
 * This enables JavaScript, file access, and DOM storage.
 */
fun WebViewState.setup() {
    this.webSettings.apply {
        isJavaScriptEnabled = true
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true

        androidWebSettings.apply {
            domStorageEnabled = true
            allowFileAccess = true
        }

    }
}
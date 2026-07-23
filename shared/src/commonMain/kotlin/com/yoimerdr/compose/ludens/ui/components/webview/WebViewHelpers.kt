package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.PlatformWebViewParams
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
 * Applies shared web settings required by the game runtime.
 *
 * Enables JavaScript execution, local file access, DOM storage, and autoplay behavior
 * used by resource-backed HTML game content.
 */
fun WebViewState.setup() {
    this.webSettings.apply {
        isJavaScriptEnabled = true
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true

        androidWebSettings.apply {
            domStorageEnabled = true
            allowFileAccess = true
            mediaPlaybackRequiresUserGesture = false
            isAlgorithmicDarkeningAllowed = false
        }

        iOSWebSettings.apply {
            mediaPlaybackRequiresUserGesture = false
        }
    }
}

/**
 * Remembers platform-specific web view parameters.
 *
 * Platform implementations can provide native clients/delegates and behavior customizations
 * while keeping call sites in common code unchanged.
 */
@Composable
internal expect fun rememberPlatformsParameters(): PlatformWebViewParams

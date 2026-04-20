package com.yoimerdr.compose.ludens.features.home.presentation.sections

import com.multiplatform.webview.web.NativeWebView

/**
 * Configures WKWebView defaults for in-page media playback and stable incremental rendering.
 */
internal actual fun setup(webView: NativeWebView) {
    webView.configuration.apply {
        allowsInlineMediaPlayback = true
        suppressesIncrementalRendering = false
    }
}

package com.yoimerdr.compose.ludens.features.home.presentation.sections

import android.graphics.Color
import android.os.Build
import com.multiplatform.webview.web.NativeWebView

/**
 * Configures Android WebView defaults for game rendering and media behavior.
 */
internal actual fun setup(webView: NativeWebView) {
    webView.setBackgroundColor(Color.TRANSPARENT)
    // Force the WebView to be focusable to improve rendering, interaction,
    // and avoiding rendering issues.
    webView.isFocusable = true
    webView.isFocusableInTouchMode = true

    webView.settings.apply {
        allowContentAccess = true
        setNeedInitialFocus(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Enable offscreen pre-rasterization for better performance on newer Android versions
            // Also avoid some rendering issues with WebView for MZ engine.
            offscreenPreRaster = true
        }
    }
}

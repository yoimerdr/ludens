package com.yoimerdr.compose.ludens.ui.components.webview

import android.os.Build
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.view.postDelayed
import com.multiplatform.webview.web.AccompanistWebChromeClient
import com.multiplatform.webview.web.AccompanistWebViewClient
import com.multiplatform.webview.web.PlatformWebViewParams

/**
 * WebView client with Android-specific rendering workarounds for game pages.
 */
private class GameWebViewClient : AccompanistWebViewClient() {
    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        // Fallback for WebView rendering issues on older Android versions
        // Some devices and Android versions have known issues with WebView rendering, especially after loading content.
        // This workaround forces the WebView to redraw itself after the page has finished loading, which can help mitigate these issues.
        // But it is not sure if it works how `offscreenPreRaster` works on newer Android versions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            view.postDelayed(300) {
                view.invalidate()
                view.post { view.invalidate() }
            }
        }

    }
}


/**
 * Remembers Android-specific web view params with custom clients.
 */
@Composable
internal actual fun rememberPlatformsParameters(): PlatformWebViewParams = remember {
    PlatformWebViewParams(
        client = GameWebViewClient(),
        chromeClient = AccompanistWebChromeClient(),
    )
}

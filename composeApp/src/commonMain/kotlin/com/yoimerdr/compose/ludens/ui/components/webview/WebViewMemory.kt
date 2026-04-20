package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import ludens.composeapp.generated.resources.Res

const val WebViewMemoryCleanerFile = "files/boot/js/memory.cleaner.js"

/**
 * Clears JavaScript-side caches by executing the bundled memory cleaner script.
 *
 * The script is loaded from compose resources and evaluated in the current web context.
 */
suspend fun WebViewNavigator.clearJSCache() {
    val code = Res.readBytes(WebViewMemoryCleanerFile).decodeToString()

    evaluateJavaScript(code)
}


/**
 * Registers platform-specific memory pressure handlers for the current web view.
 *
 * Implementations should react to low-memory signals and release non-critical web content
 * caches to keep the runtime stable.
 */
@Composable
internal expect fun WebViewMemoryManager(state: WebViewState, navigator: WebViewNavigator)


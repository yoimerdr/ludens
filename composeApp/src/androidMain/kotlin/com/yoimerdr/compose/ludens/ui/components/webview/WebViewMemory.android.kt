package com.yoimerdr.compose.ludens.ui.components.webview

import android.content.ComponentCallbacks2
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import kotlinx.coroutines.launch

/**
 * Android implementation that reacts to app memory pressure callbacks.
 *
 * When the system reports low memory, this clears native WebView cache entries and runs
 * the JavaScript memory cleaner to reduce retained web resources.
 */
@Composable
internal actual fun WebViewMemoryManager(state: WebViewState, navigator: WebViewNavigator) {
    val context = LocalContext.current

    DisposableEffect(context, navigator, state) {
        val memoryCallback = object : ComponentCallbacks2 {
            /**
             * Clears native and JavaScript-managed caches for the active web view.
             */
            private fun clearCaches() {
                state.nativeWebView.clearCache(false)
                navigator.coroutineScope.launch {
                    navigator.clearJSCache()
                }
            }

            override fun onTrimMemory(level: Int) {
                if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
                    clearCaches()
            }

            override fun onConfigurationChanged(newConfig: Configuration) {}


            @Deprecated("Deprecated in Java")
            override fun onLowMemory() {
                clearCaches()
            }
        }

        context.applicationContext.registerComponentCallbacks(memoryCallback)

        onDispose {
            context.applicationContext.unregisterComponentCallbacks(memoryCallback)
        }
    }
}

package com.yoimerdr.compose.ludens.ui.components.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.multiplatform.webview.web.WKNavigationDelegate
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.launch
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.Foundation.performSelector
import platform.UIKit.UIApplicationDidReceiveMemoryWarningNotification
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

/**
 * Navigation delegate that reloads the page if the WebKit content process is terminated.
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
private class NavigatorDelegate(
    state: WebViewState,
    navigator: WebViewNavigator,
) : NSObject(), WKNavigationDelegateProtocol by WKNavigationDelegate(state, navigator) {

    @OptIn(ExperimentalForeignApi::class)
    override fun webViewWebContentProcessDidTerminate(webView: WKWebView) {
        webView.performSelector(
            aSelector = NSSelectorFromString("reload"),
            withObject = null,
            afterDelay = 0.1
        )
    }
}


/**
 * iOS implementation that handles memory warnings and WebKit process termination.
 *
 * It runs JavaScript cache cleanup when UIKit emits memory warnings and installs a
 * navigation delegate that reloads the page after web content process termination.
 */
@Composable
internal actual fun WebViewMemoryManager(state: WebViewState, navigator: WebViewNavigator) {
    DisposableEffect(state, navigator) {
        val observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = UIApplicationDidReceiveMemoryWarningNotification,
            `object` = null,
            queue = null
        ) { _ ->
            navigator.apply {
                coroutineScope.launch {
                    clearJSCache()
                }
            }
        }

        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(observer)
        }
    }

    val webView = state.nativeWebView
    LaunchedEffect(webView, state) {
        webView.setNavigationDelegate(NavigatorDelegate(state, navigator))
    }
}

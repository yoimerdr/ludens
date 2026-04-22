package com.yoimerdr.compose.ludens.features.home.presentation.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.WebContent
import com.multiplatform.webview.web.WebStateSaver
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewFileReadType
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.yoimerdr.compose.ludens.app.ui.providers.LocalWebViewNavigator
import com.yoimerdr.compose.ludens.features.home.presentation.sections.LudensLoaderHandler.onLoad
import com.yoimerdr.compose.ludens.features.home.presentation.viewmodel.HomeViewModel
import com.yoimerdr.compose.ludens.generated.res.FileRes
import com.yoimerdr.compose.ludens.ui.components.webview.WebViewMemoryManager
import com.yoimerdr.compose.ludens.ui.components.webview.rememberPlatformsParameters
import com.yoimerdr.compose.ludens.ui.components.webview.rememberWebViewJsBridge
import com.yoimerdr.compose.ludens.ui.components.webview.setup
import com.yoimerdr.compose.ludens.ui.state.PluginState
import kotlinx.serialization.json.Json
import ludens.composeapp.generated.resources.Res

/**
 * The path to the JavaScript plugin checker file that validates and reports plugin information.
 */
const val PluginCheckerFile = FileRes.boot.js.plugin_checker

/**
 * JavaScript message handler that processes plugin loading events from the web view.
 *
 * This handler listens for messages from the "LudensLoader" JavaScript bridge and
 * deserializes plugin state information. When a plugin is loaded, it invokes the
 * registered [onLoad] callback with the parsed plugin state.
 */
private object LudensLoaderHandler : IJsMessageHandler {
    /**
     * Callback invoked when a plugin is loaded, receiving the plugin state.
     */
    var onLoad: ((PluginState) -> Unit)? = null

    override fun methodName(): String = "LudensLoader"

    override fun handle(
        message: JsMessage,
        navigator: WebViewNavigator?,
        callback: (String) -> Unit,
    ) {
        val plugin = try {
            Json.decodeFromString<PluginState?>(message.params)
        } catch (_: Exception) {
            null
        }

        onLoad?.invoke(plugin ?: PluginState())
    }
}

/**
 * Displays a web view for running HTML5 games with plugin detection.
 *
 * This composable creates a web view that loads an HTML file from resources and
 * sets up a JavaScript bridge to communicate with the loaded game. It automatically
 * injects a plugin checker script to detect and report `YDP_Ludens` plugin information.
 *
 * The web view state is persisted across configuration changes using [WebStateSaver].
 *
 * @param modifier The modifier to be applied to the web view
 * @param fileUrl The URL/path of the HTML file to load from compose resources
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 */
@Composable
fun WebGame(
    modifier: Modifier = Modifier,
    fileUrl: String,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val navigator = LocalWebViewNavigator.current
    val state = rememberSaveable(saver = WebStateSaver) {
        WebViewState(WebContent.NavigatorOnly)
    }
    val bridge = rememberWebViewJsBridge()

    val focusRequester = remember { FocusRequester() }

    state.setup()

    LaunchedEffect(navigator, fileUrl) {
        navigator.loadHtmlFile(
            fileUrl,
            WebViewFileReadType.COMPOSE_RESOURCE_FILES,
        )
    }

    LaunchedEffect(state, state.loadingState) {
        when (state.loadingState) {
            is LoadingState.Finished,
            is LoadingState.Initializing,
                -> focusRequester.requestFocus()

            else -> {}
        }
    }

    StartBridge(
        navigator = navigator,
        state = state,
        bridge = bridge,
        onLoad = onLoad,
    )

    WebViewMemoryManager(state, navigator)

    WebView(
        state = state,
        modifier = modifier.fillMaxSize().focusRequester(focusRequester),
        navigator = navigator,
        webViewJsBridge = bridge,
        platformWebViewParams = rememberPlatformsParameters(),
        onCreated = ::setup
    )
}

/**
 * Displays a web view for running HTML5 games using URL data from [HomeViewModel].
 *
 * This composable creates a web view that loads an HTML file from resources and
 * sets up a JavaScript bridge to communicate with the loaded game. It automatically
 * injects a plugin checker script to detect and report `YDP_Ludens` plugin information.
 *
 * The web view state is persisted across configuration changes using [WebStateSaver].
 *
 * @param viewModel The view model for managing the entry state
 * @param modifier The modifier to be applied to the web view
 * @param onLoad Optional callback invoked when the game plugin is loaded, receiving the plugin state
 */
@Composable
fun WebGame(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onLoad: ((PluginState) -> Unit)? = null,
) {
    val entry by viewModel.entryState.collectAsStateWithLifecycle()

    WebGame(
        modifier = modifier,
        fileUrl = entry.url,
        onLoad = onLoad,
    )
}

/**
 * Initializes and manages the JavaScript bridge for plugin detection.
 *
 * This internal composable handles the setup of the JavaScript bridge by:
 * 1. Loading the `YDP_Ludens` plugin checker script from resources.
 * 2. Registering [LudensLoaderHandler] to receive plugin load events.
 * 3. Monitoring web view loading state.
 * 4. Injecting the plugin checker script after page load completes.
 *
 * The plugin checker script is executed after the web view finishes loading,
 * The script reports plugin information back through the JavaScript bridge.
 *
 * @param navigator The web view navigator for executing JavaScript
 * @param state The web view state to monitor loading progress
 * @param bridge The JavaScript bridge for registering message handlers
 * @param onLoad Optional callback invoked when plugin information is received
 */
@Composable
private fun StartBridge(
    navigator: WebViewNavigator,
    state: WebViewState,
    bridge: WebViewJsBridge,
    onLoad: ((PluginState) -> Unit)?,
) {
    LudensLoaderHandler.onLoad = onLoad
    var pluginChecker by rememberSaveable { mutableStateOf<String?>(null) }
    var loaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        pluginChecker = Res.readBytes(PluginCheckerFile).decodeToString()
    }

    LaunchedEffect(bridge) {
        bridge.register(LudensLoaderHandler)
    }
    LaunchedEffect(state.loadingState) {
        if (state.loadingState is LoadingState.Finished && state.lastLoadedUrl.isNullOrEmpty()
                .not()
        ) {
            loaded = true
        }
    }

    LaunchedEffect(loaded, pluginChecker) {
        if (loaded && pluginChecker != null) {
            navigator.evaluateJavaScript(pluginChecker!!)
        }
    }

}

/**
 * Configures the native web view with platform-specific game optimizations.
 */
internal expect fun setup(webView: NativeWebView)

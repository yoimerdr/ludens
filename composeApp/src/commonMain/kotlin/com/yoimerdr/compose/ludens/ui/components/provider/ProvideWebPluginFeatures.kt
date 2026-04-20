package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState

/**
 * CompositionLocal for providing [PluginState] throughout the composition tree.
 *
 * This local provides the state of the YDP_Ludens plugin, including its loading
 * and enabled status. Defaults to a plugin in non-loading state.
 * Can be overridden using [ProvideWebPlugin] to provide updated plugin state
 * for specific sections of the UI.
 *
 * @see ProvideWebPlugin
 * @see PluginState
 */
val LocalPlugin = staticCompositionLocalOf { PluginState(isLoading = false) }

/**
 * CompositionLocal for providing [WebFeaturesState] throughout the composition tree.
 *
 * This local provides information about web feature support, such as WebGL and WebAudio
 * availability. Defaults to a state with no features supported.
 * Can be overridden using [ProvideWebPlugin] to provide actual feature detection
 * results for specific sections of the UI.
 *
 * @see ProvideWebPlugin
 * @see WebFeaturesState
 */
val LocalWebFeatures = staticCompositionLocalOf { WebFeaturesState() }


/**
 * Provides plugin and web features state to the composition tree.
 *
 * This composable allows you to provide both [PluginState] and [WebFeaturesState]
 * to child composables. This is typically used at a high level in the composition
 * to make plugin status and web capabilities available throughout the application.
 * All child composables can access these states through [LocalPlugin] and [LocalWebFeatures].
 *
 * @param plugin The current state of the YDP_Ludens plugin.
 * @param features The detected web features support state.
 * @param content The composable content that will have access to the provided states.
 *
 * @see LocalPlugin
 * @see LocalWebFeatures
 * @see PluginState
 * @see WebFeaturesState
 */
@Composable
@NonSkippableComposable
fun ProvideWebPlugin(
    plugin: PluginState,
    features: WebFeaturesState,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalPlugin provides plugin,
        LocalWebFeatures provides features,
        content = content
    )
}
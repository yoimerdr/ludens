package com.yoimerdr.compose.ludens.features.home.presentation.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.toQueryParameters
import ludens.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.MissingResourceException
import org.koin.compose.koinInject

/**
 * The path to the main index HTML file for the web view.
 */
const val IndexFilename = "files/www/index.html"

/**
 * The path to the fallback HTML file shown when the main index is not available.
 */
const val FallbackIndexFilename = "files/fallback/not_available.html"

/**
 * The path to the debug fallback HTML file shown when in debug mode and the main index is not available.
 */
const val DebugFallbackIndexFilename = "files/fallback/debug_not_available.html"

/**
 * Represents the state of the entry point for the home screen web view.
 *
 * @property url The URL to load in the web view
 * @property isAvailable Whether the main index file is available
 */
@Immutable
data class EntryState(
    val url: String,
    val isAvailable: Boolean,
)

/**
 * A [listSaver] for persisting and restoring [EntryState] across configuration changes.
 */
val EntryStateSaver = listSaver(
    save = { listOf(it.url, it.isAvailable) },
    restore = {
        EntryState(
            url = it[0] as String,
            isAvailable = it[1] as Boolean,
        )
    }
)

/**
 * Remembers and creates an [EntryState] for the home screen entry point.
 *
 * This function determines the appropriate URL to load based on resource availability
 * and configures web features according to the provided settings.
 *
 * @param features The web features state to apply to the entry URL
 * @param isLoading Whether the content is currently loading
 * @param settings The state containing settings
 * @param application The platform application instance for checking debug mode
 * @return An [EntryState] containing the URL and availability status
 */
@Composable
fun rememberEntryState(
    features: WebFeaturesState,
    isLoading: Boolean,
    settings: ToolSettingsState,
    application: PlatformApplication = koinInject(),
): EntryState {
    return rememberSaveable(isLoading, features, saver = EntryStateSaver) {
        val feat = features.copy(
            supportsWebAudio = if (features.supportsWebAudio)
                !settings.isMuted
            else false
        )
        val parameters = feat.toQueryParameters()
        val result = try {
            Res.getUri(IndexFilename) + "?$parameters" to true
        } catch (_: MissingResourceException) {
            Res.getUri(
                if (application.isDebug)
                    DebugFallbackIndexFilename
                else FallbackIndexFilename
            ) to false
        }

        EntryState(
            url = result.first,
            isAvailable = result.second,
        )
    }
}
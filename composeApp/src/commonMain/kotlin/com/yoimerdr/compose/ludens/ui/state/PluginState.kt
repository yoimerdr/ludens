package com.yoimerdr.compose.ludens.ui.state

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import kotlinx.serialization.Serializable

/**
 * Represents the state of the `YDP_Ludens` plugin.
 *
 * @property isEnabled Whether the plugin is enabled.
 * @property isLoading Whether the plugin is currently loading.
 */
@Serializable
data class PluginState(
    val isEnabled: Boolean = false,
    val isLoading: Boolean = true,
)

/**
 * A [Saver] for [PluginState].
 */
val PluginStateSaver = listSaver(
    save = {
        listOf(it.isLoading, it.isEnabled)
    },
    restore = {
        PluginState(
            isEnabled = it[0],
            isLoading = it[1],
        )
    }
)

/**
 * Whether the plugin is available for use.
 */
val PluginState.isAvailable: Boolean
    get() = !isLoading && isEnabled
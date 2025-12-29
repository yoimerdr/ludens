package com.yoimerdr.compose.ludens.ui.state

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import kotlinx.serialization.Serializable

/**
 * Represents the state of web features.
 *
 * @property supportsWebGL Whether WebGL is supported.
 * @property supportsWebAudio Whether WebAudio is supported.
 */
@Serializable
data class WebFeaturesState(
    val supportsWebGL: Boolean = false,
    val supportsWebAudio: Boolean = false,
)

/**
 * Converts the [WebFeaturesState] to a list of parameters for RPG Maker.
 *
 * @return A list of strings representing the parameters.
 */
fun WebFeaturesState.toParameters(): List<String> {
    val params = mutableListOf<String>()

    if (supportsWebGL) params.add("webgl") // canvas

    if (!supportsWebAudio)
        params.add("noaudio")

    return params
}

/**
 * Converts the [WebFeaturesState] to a query string that RPG Maker can parse.
 *
 * @return A string representing the query parameters.
 */
fun WebFeaturesState.toQueryParameters(): String {
    return toParameters()
        .joinToString("&")
}

/**
 * A [Saver] for [WebFeaturesState].
 */
val WebFeaturesStateSaver = listSaver(
    save = {
        listOf(
            it.supportsWebGL,
            it.supportsWebAudio,
        )
    },
    restore = {
        WebFeaturesState(
            supportsWebGL = it[0],
            supportsWebAudio = it[1],
        )
    }
)

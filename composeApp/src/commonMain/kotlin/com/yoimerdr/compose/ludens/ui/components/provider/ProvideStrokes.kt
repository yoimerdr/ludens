package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.components.layout.DefaultStrokes
import com.yoimerdr.compose.ludens.ui.components.layout.Strokes

/**
 * CompositionLocal for providing [Strokes] values throughout the composition tree.
 *
 * Defaults to [DefaultStrokes].
 * Can be overridden using [ProvideStrokes] to provide custom stroke values
 * for specific sections of the UI.
 *
 * @see ProvideStrokes
 * @see Strokes
 */
val LocalStrokes = staticCompositionLocalOf<Strokes> { DefaultStrokes }


/**
 * Provides custom [Strokes] values to the composition tree.
 *
 * This composable allows you to override the default stroke scale for a specific
 * section of the UI. All child composables can access the provided strokes through
 * [LocalStrokes].
 *
 * @param strokes The custom strokes implementation to provide.
 * @param content The composable content that will have access to the provided strokes.
 *
 * @see LocalStrokes
 * @see Strokes
 */
@Composable
@NonSkippableComposable
fun ProvideStrokes(
    strokes: Strokes,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalStrokes provides strokes,
        content = content,
    )
}

package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.components.layout.DefaultRadius
import com.yoimerdr.compose.ludens.ui.components.layout.Radius

/**
 * CompositionLocal for providing [Radius] values throughout the composition tree.
 *
 * Defaults to [DefaultRadius].
 * Can be overridden using [ProvideRadius] to provide custom radius values
 * for specific sections of the UI.
 *
 * @see ProvideRadius
 * @see Radius
 */
val LocalRadius = staticCompositionLocalOf<Radius> { DefaultRadius }


/**
 * Provides custom [Radius] values to the composition tree.
 *
 * This composable allows you to override the default radius scale for a specific
 * section of the UI. All child composables can access the provided radius through
 * [LocalRadius].
 *
 * @param radius The custom radius implementation to provide.
 * @param content The composable content that will have access to the provided radius.
 *
 * @see LocalRadius
 * @see Radius
 */
@Composable
@NonSkippableComposable
fun ProvideRadius(
    radius: Radius,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalRadius provides radius,
        content = content,
    )
}

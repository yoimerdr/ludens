package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.components.layout.DefaultSpacing
import com.yoimerdr.compose.ludens.ui.components.layout.Spacing

/**
 * CompositionLocal for providing [Spacing] values throughout the composition tree.
 *
 * Defaults to [DefaultSpacing] which follows Material Design 3 spacing scale.
 * Can be overridden using [ProvideSpacing] to provide custom spacing values
 * for specific sections of the UI.
 *
 * @see ProvideSpacing
 * @see Spacing
 */
val LocalSpacing = staticCompositionLocalOf<Spacing> { DefaultSpacing }


/**
 * Provides custom [Spacing] values to the composition tree.
 *
 * This composable allows you to override the default spacing scale for a specific
 * section of the UI. All child composables can access the provided spacing through
 * [LocalSpacing].
 *
 * @param spacing The custom spacing implementation to provide.
 * @param content The composable content that will have access to the provided spacing.
 *
 * @see LocalSpacing
 * @see Spacing
 */
@Composable
@NonSkippableComposable
fun ProvideSpacing(
    spacing: Spacing,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSpacing provides spacing,
        content = content,
    )
}
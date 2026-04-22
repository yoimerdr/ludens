package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.components.layout.Breakpoints
import com.yoimerdr.compose.ludens.ui.components.layout.DefaultBreakpoints

/**
 * CompositionLocal for providing [Breakpoints] values throughout the composition tree.
 *
 * Defaults to [DefaultBreakpoints] which follows a standard responsive breakpoint scale for mobiles.
 * Can be overridden using [ProvideBreakpoints] to provide custom breakpoint values
 * for specific sections of the UI.
 *
 * @see ProvideBreakpoints
 * @see Breakpoints
 */
val LocalBreakpoints = staticCompositionLocalOf<Breakpoints> { DefaultBreakpoints }


/**
 * Provides custom [Breakpoints] values to the composition tree.
 *
 * This composable allows you to override the default breakpoint scale for a specific
 * section of the UI. All child composables can access the provided breakpoints through
 * [LocalBreakpoints].
 *
 * @param breakpoints The custom breakpoints implementation to provide.
 * @param content The composable content that will have access to the provided breakpoints.
 *
 * @see LocalBreakpoints
 * @see Breakpoints
 */
@Composable
@NonSkippableComposable
fun ProvideBreakpoints(
    breakpoints: Breakpoints,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalBreakpoints provides breakpoints,
        content = content,
    )
}

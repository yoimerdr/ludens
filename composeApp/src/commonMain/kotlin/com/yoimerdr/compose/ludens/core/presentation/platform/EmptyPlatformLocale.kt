package com.yoimerdr.compose.ludens.core.presentation.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage

/**
 * Empty implementation of [PlatformLocale] for testing and preview purposes.
 *
 * This implementation provides a minimal, non-functional version of the [PlatformLocale]
 * interface that can be safely used in `@Preview` composables without causing issues
 * with platform-specific APIs that may not be available in preview mode.
 *
 * All methods return default or no-op implementations, making it suitable for UI
 * testing scenarios where actual locale functionality is not required.
 */
object EmptyPlatformLocale : PlatformLocale {
    /**
     * Backing field for the composition local provider.
     *
     * Lazily initialized to avoid unnecessary allocation if not needed.
     */
    private var _provider: ProvidableCompositionLocal<SystemLanguage>? = null

    /**
     * Gets or creates the composition local provider.
     *
     * @return A [ProvidableCompositionLocal] that provides [SystemLanguage.System] by default.
     */
    private val provider: ProvidableCompositionLocal<SystemLanguage>
        get() {
            if (_provider == null) {
                _provider = staticCompositionLocalOf { SystemLanguage.System }
            }
            return _provider!!
        }


    override val current: SystemLanguage
        @Composable
        @ReadOnlyComposable
        get() = SystemLanguage.System


    @Composable
    override fun provides(value: SystemLanguage?): ProvidedValue<*> {
        return provider provides (value ?: SystemLanguage.System)
    }


    @Composable
    override fun ProvidesLocale(
        value: SystemLanguage?,
        content: @Composable (() -> Unit),
    ) {
        content()
    }
}
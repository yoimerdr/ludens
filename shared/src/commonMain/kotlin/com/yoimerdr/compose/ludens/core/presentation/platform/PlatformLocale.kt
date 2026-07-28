package com.yoimerdr.compose.ludens.core.presentation.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage

/**
 * Platform-specific locale management interface.
 *
 * Provides functionality to access and manage the system language/locale
 * in a platform-agnostic way for Compose Multiplatform applications.
 */
interface PlatformLocale {
    /**
     * Gets the current system language.
     *
     * @return The current [SystemLanguage] of the platform.
     */
    @get:Composable
    @get:ReadOnlyComposable
    val current: SystemLanguage

    /**
     * Creates a [ProvidedValue] for the given language.
     *
     * This allows providing a specific language value to the composition local tree.
     *
     * @param value The [SystemLanguage] to provide, or null to use the system default.
     * @return A [ProvidedValue] that can be used with CompositionLocalProvider.
     */
    @Composable
    infix fun provides(value: SystemLanguage?): ProvidedValue<*>

    /**
     * Provides a specific locale to the composition.
     *
     * Wraps the content with the provided language context, allowing child composables
     * to access the specified language.
     *
     * @param value The [SystemLanguage] to provide, or null to use the system default.
     * @param content The composable content that will have access to the provided locale.
     */
    @Composable
    fun ProvidesLocale(value: SystemLanguage?, content: @Composable () -> Unit)
}
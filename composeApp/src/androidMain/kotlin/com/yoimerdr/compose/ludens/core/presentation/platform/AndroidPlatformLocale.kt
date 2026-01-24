package com.yoimerdr.compose.ludens.core.presentation.platform

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import java.util.Locale

/**
 * Android implementation of [PlatformLocale].
 *
 * Manages locale and language configuration for Android platform using
 * Android's [Locale] and [Configuration] APIs.
 */
object AndroidPlatformLocale : PlatformLocale {
    /**
     * The default system locale captured at initialization.
     *
     * This is stored to allow resetting to the original system language.
     */
    var default: Locale? = null
        private set

    /**
     * Gets the current system language from Android's default locale.
     *
     * @return The current [SystemLanguage] based on the device's locale settings.
     */
    override val current: SystemLanguage
        @Composable
        @ReadOnlyComposable
        get() {
            val locale = Locale.getDefault().toString()

            return SystemLanguage.fromLocaleTag(locale)!!
        }

    /**
     * Validates and converts a [SystemLanguage] to an Android [Locale].
     *
     * If the value is null or [SystemLanguage.System], returns the default system locale.
     * Otherwise, converts the language tag to a [Locale].
     *
     * @param value The [SystemLanguage] to validate, or null for system default.
     * @return The corresponding Android [Locale].
     */
    private fun validateLocale(value: SystemLanguage?): Locale {
        if (default == null) {
            default = Locale.getDefault()
        }
        return when (value) {
            null, SystemLanguage.System -> default!!
            else -> Locale.forLanguageTag(value.tag)
        }
    }

    @Composable
    private fun rememberLocaleContext(language: SystemLanguage?): Context {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current

        val locale = validateLocale(language)

        return remember(locale) {
            val config = Configuration(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            context.createConfigurationContext(config)
        }
    }

    @Composable
    @Suppress("DEPRECATION")
    override fun provides(value: SystemLanguage?): ProvidedValue<*> {
        val context = rememberLocaleContext(value)

        return LocalConfiguration.provides(context.resources.configuration)
    }

    @Composable
    override fun ProvidesLocale(
        value: SystemLanguage?,
        content: @Composable (() -> Unit),
    ) {
        val context = rememberLocaleContext(value)

        CompositionLocalProvider(
            LocalContext provides context,
            LocalConfiguration provides context.resources.configuration,
            content = content
        )
    }
}
package com.yoimerdr.compose.ludens.core.presentation.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

/**
 * iOS implementation of [PlatformLocale].
 *
 * Manages locale and language configuration for iOS platform using
 * iOS's [NSLocale] and [NSUserDefaults] APIs.
 */
object IOSPlatformLocale : PlatformLocale {
    /**
     * The key used to store language preferences in [NSUserDefaults].
     */
    const val LANG_KEY = "AppleLanguages"

    /**
     * The default system language tag from iOS preferred languages.
     */
    val defaultLanguage = NSLocale.preferredLanguages.first() as String

    /**
     * The default [SystemLanguage] derived from the system's preferred language.
     */
    val default = SystemLanguage.fromLocaleTag(defaultLanguage)!!

    /**
     * CompositionLocal provider for the current locale in iOS.
     */
    private val Provider = staticCompositionLocalOf { default }

    /**
     * Validates and converts a [SystemLanguage] to a concrete language.
     *
     * If the value is null or [SystemLanguage.System], returns the default system language.
     * Otherwise, returns the provided language.
     *
     * @param language The [SystemLanguage] to validate, or null for system default.
     * @return The validated [SystemLanguage].
     */
    private fun validateLocale(language: SystemLanguage?): SystemLanguage {
        return when (language) {
            null, SystemLanguage.System -> default
            else -> language
        }
    }

    /**
     * Sets the locale in iOS [NSUserDefaults].
     *
     * If the language is [SystemLanguage.System], removes the custom language setting
     * to use the system default. Otherwise, stores the language tag in user defaults.
     *
     * @param language The [SystemLanguage] to set.
     */
    private fun setLocale(language: SystemLanguage) {
        if (language == SystemLanguage.System) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey(LANG_KEY)
        } else {
            NSUserDefaults.standardUserDefaults.setObject(
                arrayListOf(language.tag),
                LANG_KEY
            )
        }
    }


    override val current: SystemLanguage
        @Composable
        @ReadOnlyComposable
        get() = Provider.current

    @Composable
    override fun provides(value: SystemLanguage?): ProvidedValue<*> {
        val language = validateLocale(value)
        setLocale(language)
        return Provider.provides(language)
    }

    @Composable
    override fun ProvidesLocale(
        value: SystemLanguage?,
        content: @Composable (() -> Unit),
    ) {
        CompositionLocalProvider(
            provides(value),
            content = content
        )
    }
}
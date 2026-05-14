package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Font selection and fallback configuration used by the Compose resource generators.
 *
 * The base `display` and `body` fonts are copied from the asset store into Compose resources
 * and then used to generate `FontFamily` declarations. Language-specific overrides can supply
 * extra fallback fonts per language tag.
 */
data class LudensFontConfiguration(
    /**
     * Font file name used for display text and headings.
     */
    val display: String = "plusjakartasans.ttf",
    /**
     * Font file name used for body text.
     */
    val body: String = "inter.ttf",
    /**
     * Optional per-language font overrides keyed by language tag.
     */
    val language: Map<String, LudensLanguageFont> = emptyMap(),
) : Serializable

/**
 * Per-language font overrides.
 *
 * Each language tag can optionally provide a dedicated `display` or `body` font file name.
 * These values are treated as fallbacks and are only used when the language is active and the
 * corresponding file exists in the asset font directory.
 */
data class LudensLanguageFont(
    /**
     * Optional display font override for this language.
     */
    val display: String = "",
    /**
     * Optional body font override for this language.
     */
    val body: String = "",
) : Serializable

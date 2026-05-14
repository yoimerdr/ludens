package ludens.build.compose.configuration

import java.io.Serializable

/**
 * Settings configuration loaded from `ludens.properties`.
 *
 * The block selects a preset and optionally overrides its raw values before generation.
 */
data class LudensSettingsConfiguration(
    /**
     * Preset name to generate.
     *
     * Maps from `ludens.settings.presetName`. When absent, the generator falls back to the
     * built-in `recommended` preset name.
     */
    val presetName: String? = null,
    /**
     * Raw preset values.
     *
     * Maps from `ludens.settings.preset.*`. These values are parsed and normalized by
     * [resolvedSettingsPreset] before the generated Kotlin source is written.
     */
    val preset: LudensSettingsPresetConfiguration = LudensSettingsPresetConfiguration(),
) : Serializable

/**
 * Raw settings preset values loaded from `ludens.settings.preset.*`.
 *
 * The resolver normalizes booleans, floating point values and default fallbacks before
 * generating `LudensSettingsPreset.kt`.
 */
data class LudensSettingsPresetConfiguration(
    /**
     * Toggles the muted tool default.
     *
     * Maps from `ludens.settings.preset.toolMuted`.
     */
    val toolMuted: String? = null,
    /**
     * Toggles the FPS tool default.
     *
     * Maps from `ludens.settings.preset.toolShowFps`.
     */
    val toolShowFps: String? = null,
    /**
     * Toggles the WebGL tool default.
     *
     * Maps from `ludens.settings.preset.toolUseWebgl`.
     */
    val toolUseWebgl: String? = null,
    /**
     * Toggles on-screen controls.
     *
     * Maps from `ludens.settings.preset.controlEnabled`.
     */
    val controlEnabled: String? = null,
    /**
     * Controls the alpha value of the virtual controls.
     *
     * Maps from `ludens.settings.preset.controlAlpha`.
     */
    val controlAlpha: String? = null,
    /**
     * Toggles quick actions.
     *
     * Maps from `ludens.settings.preset.actionEnabled`.
     */
    val actionEnabled: String? = null,
    /**
     * Quick action item list.
     *
     * Maps from `ludens.settings.preset.actionItems`.
     */
    val actionItems: String? = null,
    /**
     * System theme default.
     *
     * Maps from `ludens.settings.preset.systemTheme`.
     */
    val systemTheme: String? = null,
    /**
     * System language default.
     *
     * Maps from `ludens.settings.preset.systemLanguage`.
     */
    val systemLanguage: String? = null,
) : Serializable

/**
 * Resolved settings preset after parsing and defaults have been applied.
 *
 * This is the final in-memory representation consumed by the generation task.
 */
data class LudensResolvedSettingsPresetConfiguration(
    /** Generated preset name written to the Kotlin source. */
    val presetName: String,
    /** Default value for the muted tool flag. */
    val toolMuted: Boolean,
    /** Default value for the FPS overlay flag. */
    val toolShowFps: Boolean,
    /** Default value for the WebGL flag. */
    val toolUseWebgl: Boolean,
    /** Default value for on-screen controls. */
    val controlEnabled: Boolean,
    /** Default alpha for on-screen controls. */
    val controlAlpha: Float,
    /** Default value for quick actions. */
    val actionEnabled: Boolean,
    /** Quick action item list written into the generated preset. */
    val actionItems: String,
    /** Default system theme value. */
    val systemTheme: String,
    /** Default system language value. */
    val systemLanguage: String,
) : Serializable

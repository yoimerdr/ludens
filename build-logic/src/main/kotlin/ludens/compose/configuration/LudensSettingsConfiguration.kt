package ludens.build.compose.configuration

/**
 * Settings configuration loaded from `ludens.properties`.
 *
 * The block selects a preset and optionally overrides its raw values before generation.
 */
data class LudensSettingsConfiguration(
    /**
     * Preset name to generate.
     *
     * Maps from `ludens.settings.presetName`.
     */
    val presetName: String? = null,
    /**
     * Raw preset values.
     *
     * Maps from `ludens.settings.preset.*`.
     */
    val preset: LudensSettingsPresetConfiguration = LudensSettingsPresetConfiguration(),
)

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
)

/**
 * Resolved settings preset after parsing and defaults have been applied.
 *
 * This is the final in-memory representation consumed by the generation task.
 */
data class LudensResolvedSettingsPresetConfiguration(
    val presetName: String,
    val toolMuted: Boolean,
    val toolShowFps: Boolean,
    val toolUseWebgl: Boolean,
    val controlEnabled: Boolean,
    val controlAlpha: Float,
    val actionEnabled: Boolean,
    val actionItems: String,
    val systemTheme: String,
    val systemLanguage: String,
)

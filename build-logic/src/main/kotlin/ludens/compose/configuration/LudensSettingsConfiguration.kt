package ludens.build.compose.configuration

data class LudensSettingsConfiguration(
    val presetName: String? = null,
    val preset: LudensSettingsPresetConfiguration = LudensSettingsPresetConfiguration(),
)

data class LudensSettingsPresetConfiguration(
    val toolMuted: String? = null,
    val toolShowFps: String? = null,
    val toolUseWebgl: String? = null,
    val controlEnabled: String? = null,
    val controlAlpha: String? = null,
    val actionEnabled: String? = null,
    val actionItems: String? = null,
    val systemTheme: String? = null,
    val systemLanguage: String? = null,
)

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

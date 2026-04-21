package ludens.build.compose.configuration

fun LudensConfiguration.resolvedSettingsPreset(): LudensResolvedSettingsPresetConfiguration {
    val localPreset = settings.preset

    val presetName = localValue(settings.presetName)
        ?: "recommended"

    val toolMuted = parseBoolean(
        value = localValue(localPreset.toolMuted),
        default = false,
    )

    val toolShowFps = parseBoolean(
        value = localValue(localPreset.toolShowFps),
        default = false,
    )

    val toolUseWebgl = parseBoolean(
        value = localValue(localPreset.toolUseWebgl),
        default = false,
    )

    val controlEnabled = parseBoolean(
        value = localValue(localPreset.controlEnabled),
        default = true,
    )

    val controlAlpha = parseFloat(
        value = localValue(localPreset.controlAlpha),
        default = 0.4f,
    ).also {
        require(it in 0f..1f) {
            "settings.preset.controlAlpha must be between 0.0 and 1.0. Current value: $it"
        }
    }

    val actionEnabled = parseBoolean(
        value = localValue(localPreset.actionEnabled),
        default = false,
    )

    val actionItems = localValue(localPreset.actionItems)
        ?: "settings"

    val systemTheme = localValue(localPreset.systemTheme)
        ?: "system"

    val systemLanguage = localValue(localPreset.systemLanguage)
        ?: "system"

    return LudensResolvedSettingsPresetConfiguration(
        presetName = presetName,
        toolMuted = toolMuted,
        toolShowFps = toolShowFps,
        toolUseWebgl = toolUseWebgl,
        controlEnabled = controlEnabled,
        controlAlpha = controlAlpha,
        actionEnabled = actionEnabled,
        actionItems = actionItems,
        systemTheme = systemTheme,
        systemLanguage = systemLanguage,
    )
}

private fun localValue(value: String?): String? = value?.trim()?.takeIf { it.isNotEmpty() }

private fun parseBoolean(value: String?, default: Boolean): Boolean {
    return when (value?.trim()?.lowercase()) {
        "1", "true", "yes", "on" -> true
        "0", "false", "no", "off" -> false
        else -> default
    }
}

private fun parseFloat(value: String?, default: Float): Float {
    return value?.trim()?.toFloatOrNull() ?: default
}

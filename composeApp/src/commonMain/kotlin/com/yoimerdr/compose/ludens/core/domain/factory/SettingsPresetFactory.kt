package com.yoimerdr.compose.ludens.core.domain.factory

import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionItem
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemLanguage
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemTheme
import com.yoimerdr.compose.ludens.core.domain.value.Alpha
import com.yoimerdr.compose.ludens.generated.settings.LudensSettingsPreset

/**
 * Resolves the default in-app settings from the generated build-time preset object.
 *
 * Presets are selected via `ludens.settings.presetName` and optional
 * `ludens.settings.preset.*` values. These defaults are used when the settings store
 * is created for the first time.
 */
object SettingsPresetFactory {
    private const val PRESET_RECOMMENDED = "recommended"
    private const val PRESET_NO_ACTIONS = "noactions"
    private const val PRESET_NO_CONTROLS = "nocontrols"
    private const val PRESET_MINIMALIST = "minimalist"
    private const val PRESET_CUSTOM = "custom"

    private const val RECOMMENDED_ACTIONS = "settings,controls,mute,fps"
    private const val NO_CONTROLS_ACTIONS = "settings,controls,mute,fps,webgl"

    /**
     * Resolves the full default [Settings] instance for the selected preset.
     *
     * Unknown preset names gracefully fallback to `recommended`.
     */
    fun resolve(): Settings {
        return when (normalize(LudensSettingsPreset.presetName)) {
            PRESET_NO_ACTIONS -> noActionsPreset()
            PRESET_NO_CONTROLS -> noControlsPreset()
            PRESET_MINIMALIST -> minimalistPreset()
            PRESET_CUSTOM -> customPreset()
            else -> recommendedPreset()
        }
    }

    /** Recommended game defaults with quick actions enabled. */
    private fun recommendedPreset(): Settings = SettingsFactory.settings(
        action = actionSettings(
            enabled = true,
            itemsRaw = RECOMMENDED_ACTIONS,
        )
    )

    /** Disables quick actions while keeping the rest of defaults untouched. */
    private fun noActionsPreset(): Settings {
        return SettingsFactory.settings(
            action = actionSettings(
                enabled = false,
                itemsRaw = "settings",
            ),
        )
    }

    /** Hides on-screen controls and relies on quick actions for toggles/navigation. */
    private fun noControlsPreset(): Settings = SettingsFactory.settings(
        control = SettingsFactory.controlSettings(
            enabled = false,
            alpha = Alpha.Low,
        ),
        action = actionSettings(
            enabled = true,
            itemsRaw = NO_CONTROLS_ACTIONS,
        )
    )

    /** Minimal UI footprint: no controls, no quick actions, conservative tool flags. */
    private fun minimalistPreset(): Settings {
        val control = SettingsFactory.controlSettings(
            enabled = false,
            alpha = Alpha.Low,
        )

        return SettingsFactory.settings(
            tool = SettingsFactory.toolSettings(
                isMuted = false,
                showFPS = false,
                useWebGL = false,
            ),
            control = control,
            system = SettingsFactory.systemSettings(
                theme = SystemTheme.System,
                language = SystemLanguage.System,
            ),
            action = actionSettings(
                enabled = false,
                itemsRaw = "settings",
            ),
        )
    }

    /** Builds settings from `ludens.settings.preset.*` values. */
    private fun customPreset(): Settings {
        val alpha = Alpha.coerce(LudensSettingsPreset.preset.controlAlpha)
        val control = SettingsFactory.controlSettings(
            enabled = LudensSettingsPreset.preset.controlEnabled,
            alpha = alpha,
        )

        return SettingsFactory.settings(
            tool = SettingsFactory.toolSettings(
                isMuted = LudensSettingsPreset.preset.toolMuted,
                showFPS = LudensSettingsPreset.preset.toolShowFPS,
                useWebGL = LudensSettingsPreset.preset.toolUseWebgl,
            ),
            control = control,
            system = SettingsFactory.systemSettings(
                theme = parseTheme(LudensSettingsPreset.preset.systemTheme),
                language = parseLanguage(LudensSettingsPreset.preset.systemLanguage),
            ),
            action = actionSettings(
                enabled = LudensSettingsPreset.preset.actionEnabled,
                itemsRaw = LudensSettingsPreset.preset.actionItems,
            ),
        )
    }

    /** Creates [ActionSettings] with parsed action item toggles. */
    private fun actionSettings(enabled: Boolean, itemsRaw: String): ActionSettings {
        return ActionSettings(
            enabled = enabled,
            items = toActionItems(itemsRaw),
        )
    }

    /** Maps raw action token lists to canonical [ActionItem] entries. */
    private fun toActionItems(raw: String): List<ActionItem> {
        val enabledItems = parseActionItems(raw)

        return ActionType.entries.mapIndexed { index, type ->
            ActionItem(
                type = type,
                enabled = type == ActionType.Settings || type in enabledItems,
                order = index,
            )
        }
    }

    /** Parses supported action tokens (`settings`, `controls`, `mute`, `fps`, `webgl`). */
    private fun parseActionItems(raw: String): Set<ActionType> {
        return raw.split(',', ';', '|')
            .asSequence()
            .map { normalize(it) }
            .mapNotNull { token ->
                when (token) {
                    "settings", "config" -> ActionType.Settings
                    "togglecontrols", "controls" -> ActionType.ToggleControls
                    "togglemute", "mute" -> ActionType.ToggleMute
                    "togglefps", "fps" -> ActionType.ToggleFPS
                    "togglewebgl", "webgl" -> ActionType.ToggleWebGL
                    else -> null
                }
            }
            .toSet()
    }

    /** Parses system theme aliases with fallback to [SystemTheme.System]. */
    private fun parseTheme(value: String): SystemTheme {
        return when (normalize(value)) {
            "light" -> SystemTheme.Light
            "dark" -> SystemTheme.Dark
            else -> SystemTheme.System
        }
    }

    /** Parses language aliases with fallback to [SystemLanguage.System]. */
    private fun parseLanguage(value: String): SystemLanguage {
        return when (normalize(value)) {
            "english", "en" -> SystemLanguage.English
            "spanish", "es" -> SystemLanguage.Spanish
            else -> SystemLanguage.System
        }
    }

    /** Normalizes tokens for resilient matching (`_`, `-`, spaces, case). */
    private fun normalize(value: String): String {
        return value.trim()
            .lowercase()
            .replace("_", "")
            .replace("-", "")
            .replace(" ", "")
    }

}

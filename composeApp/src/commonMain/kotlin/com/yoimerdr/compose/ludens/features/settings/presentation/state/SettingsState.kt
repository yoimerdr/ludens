package com.yoimerdr.compose.ludens.features.settings.presentation.state

import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState as SettingsStateModel

/**
 * Available sections in the settings screen.
 */
enum class SettingsSection {
    /** Control settings section. */
    Controls,

    /** Actions settings section. */
    Actions,

    /** Tools settings section. */
    Tools,

    /** System settings section. */
    System,

    /** About section. */
    About,
}

/**
 * Independent configuration categories.
 *
 * Each category represents a distinct group of settings that can be managed separately.
 */
enum class SettingsCategory {
    /** Control settings. */
    Controls,

    /** Tool settings. */
    Tools,

    /** System settings. */
    System,

    /** Action settings. */
    Actions;

    companion object {
        /**
         * Set containing all settings categories.
         */
        val All: Set<SettingsCategory> = entries.toSet()

        /**
         * Creates a set containing all categories except the specified ones.
         *
         * @param category The first category to exclude.
         * @param categories Additional categories to exclude.
         * @return A set containing all categories except the specified ones.
         *
         */
        fun excepting(category: SettingsCategory, vararg categories: SettingsCategory): Set<SettingsCategory> {
            val excluded = setOf(category) + categories
            return All - excluded
        }
    }
}

/**
 * User confirmation requests in the settings screen.
 */
sealed interface SettingsRequest {
    /** Request for mute confirmation. */
    data class RequestMute(
        val value: Boolean,
    ) : SettingsRequest

    /** Request for WebGL usage confirmation. */
    data class RequestWebGL(
        val value: Boolean,
    ) : SettingsRequest
}

/**
 * Behavioral states of the settings screen.
 */
sealed interface SettingsMode {
    /** Initial loading state. */
    object Initializing : SettingsMode

    /** Normal idle state. */
    object Idle : SettingsMode

    /** Pending confirmation mode with pending request. */
    data class PendingConfirmation(
        val request: SettingsRequest,
    ) : SettingsMode
}

/**
 * UI state for the settings screen.
 *
 * @param settings The current settings model.
 * @param section The currently selected section.
 * @param mode The current behavioral state.
 */
data class SettingsState(
    val settings: SettingsStateModel = SettingsStateModel.default,
    val section: SettingsSection = SettingsSection.Controls,
    val mode: SettingsMode = SettingsMode.Initializing,
)


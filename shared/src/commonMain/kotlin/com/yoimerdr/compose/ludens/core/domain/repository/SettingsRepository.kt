package com.yoimerdr.compose.ludens.core.domain.repository

import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ControlSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.Settings
import com.yoimerdr.compose.ludens.core.domain.model.settings.SystemSettings
import com.yoimerdr.compose.ludens.core.domain.model.settings.ToolSettings
import kotlinx.coroutines.flow.Flow

/** Type alias for a Flow of Settings */
typealias SettingsFlow = Flow<Settings>

/** Type alias for a Flow of ControlSettings */
typealias ControlSettingsFlow = Flow<ControlSettings>

/** Type alias for a Flow of ToolSettings */
typealias ToolsSettingsFlow = Flow<ToolSettings>

/** Type alias for a Flow of SystemSettings */
typealias SystemSettingsFlow = Flow<SystemSettings>

/** Type alias for a Flow of ActionSettings */
typealias ActionSettingsFlow = Flow<ActionSettings>

/**
 * Repository interface for managing application settings.
 *
 * This interface defines the contract for accessing and updating various types of settings,
 * providing reactive access through Kotlin Flows.
 */
interface SettingsRepository {
    /**
     * Retrieves tool settings as a Flow.
     * @return A Flow that emits ToolSettings whenever they change.
     */
    fun getToolSettings(): ToolsSettingsFlow

    /**
     * Updates the tool settings.
     * @param settings The new tool settings to persist.
     */
    suspend fun updateToolSettings(settings: ToolSettings)

    /**
     * Retrieves control settings as a Flow.
     * @return A Flow that emits ControlSettings whenever they change.
     */
    fun getControlSettings(): ControlSettingsFlow

    /**
     * Updates the control settings.
     * @param settings The new control settings to persist.
     */
    suspend fun updateControlSettings(settings: ControlSettings)

    /**
     * Retrieves system settings as a Flow.
     * @return A Flow that emits SystemSettings whenever they change.
     */
    fun getSystemSettings(): SystemSettingsFlow

    /**
     * Updates the system settings.
     * @param settings The new system settings to persist.
     */
    suspend fun updateSystemSettings(settings: SystemSettings)

    /**
     * Retrieves action settings as a Flow.
     * @return A Flow that emits ActionSettings whenever they change.
     */
    fun getActionSettings(): ActionSettingsFlow

    /**
     * Updates the action settings.
     * @param settings The new action settings to persist.
     */
    suspend fun updateActionSettings(settings: ActionSettings)

    /**
     * Retrieves all settings as a Flow.
     * @return A Flow that emits complete Settings whenever they change.
     */
    fun getSettings(): SettingsFlow

    /**
     * Updates all settings.
     * @param settings The new settings to persist.
     */
    suspend fun updateSettings(settings: Settings)
}
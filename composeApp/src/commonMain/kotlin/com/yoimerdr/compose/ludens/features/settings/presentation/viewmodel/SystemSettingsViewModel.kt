package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSystemSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateSystemSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SystemSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsCategory
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.OnChangeLanguage
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.OnChangeTheme
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.RestoreDefaultSettings
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestRestartingRestore
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.SettingsRequest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * View model for managing system settings section.
 *
 * This view model handles the state and events for system-level settings
 * such as theme and language preferences.
 *
 * The view model automatically loads settings from the data source and persists
 * changes with debouncing to prevent excessive write operations.
 *
 * @param getSystemSettingsUseCase Use case for retrieving system settings.
 * @param updateSystemSettingsUseCase Use case for persisting system settings.
 * @param getAllSettingsUseCase Use case for retrieving all application settings.
 * @param updateAllSettingsUseCase Use case for updating all application settings.
 */
@KoinViewModel
class SystemSettingsViewModel(
    private val getSystemSettingsUseCase: GetSystemSettingsUseCase,
    private val updateSystemSettingsUseCase: UpdateSystemSettingsUseCase,
    private val getAllSettingsUseCase: GetSettingsUseCase,
    private val updateAllSettingsUseCase: UpdateSettingsUseCase,
) : BaseSettingsViewModel<SystemSettingsState>(
    initialValue = SystemSettingsState(),
) {

    private lateinit var currentSettings: SettingsState

    /**
     * Flag indicating whether a settings request originated from restoring default settings.
     * Used to apply all default settings after a pending confirmation is resolved.
     * Contains the set of categories to reset, or null if no reset is in progress.
     */
    private var _requestFromDefaults: Set<SettingsCategory>? = null

    init {
        viewModelScope.launch {
            getAllSettingsUseCase()
                .collect {
                    currentSettings = it.toUIModel()
                }
        }
        viewModelScope.launch {
            getSystemSettingsUseCase().stateCollect { settings ->
                settings.toUIModel()
            }
        }

        viewModelScope.launch {
            persistCollect {
                updateSystemSettingsUseCase(
                    it.toDomain(),
                )
            }
        }
    }

    /**
     * Changes the application theme.
     *
     * @param event The event containing the theme to apply.
     */
    fun onChangeTheme(event: OnChangeTheme) {
        updateState {
            copy(theme = event.theme)
        }
    }

    /**
     * Changes the application language.
     *
     * @param event The event containing the language to apply.
     */
    fun onChangeLanguage(event: OnChangeLanguage) {
        updateState {
            copy(language = event.language)
        }
    }

    /**
     * Applies default values for specific settings categories.
     *
     * This method directly applies default values without requiring confirmation,
     * as it should only be called after any necessary confirmations have been resolved.
     *
     * @param categories The set of categories to reset. If empty, resets all categories.
     */
    private suspend fun applyDefaultsByCategory(categories: Set<SettingsCategory>) {
        val default = SettingsState.default
        val categoriesToReset = categories.ifEmpty { SettingsCategory.All }

        var updated = currentSettings

        if (SettingsCategory.Controls in categoriesToReset) {
            updated = updated.copy(control = default.control)
        }

        if (SettingsCategory.Tools in categoriesToReset) {
            updated = updated.copy(tool = default.tool)
        }

        if (SettingsCategory.System in categoriesToReset) {
            updated = updated.copy(system = default.system)
        }

        if (SettingsCategory.Actions in categoriesToReset) {
            updated = updated.copy(action = default.action)
        }

        updateAllSettingsUseCase(updated.toDomain())
    }

    /**
     * Resets specific settings categories to their default values.
     *
     * @param categories The set of categories to reset. If empty, resets all categories.
     */
    fun restoreDefaults(categories: Set<SettingsCategory>) {
        viewModelScope.launch {
            val categories = categories.ifEmpty { SettingsCategory.All }

            // If already processing a request from defaults, apply the changes immediately
            if (_requestFromDefaults != null) {
                applyDefaultsByCategory(categories)
                return@launch
            }

            // Check if Tools category needs to be reset and if it requires confirmation
            if (SettingsCategory.Tools in categories) {
                val default = SettingsState.default

                _requestFromDefaults = categories

                // Get current tool settings to check if confirmation is needed
                val defaultTool = default.tool
                val tool = currentSettings.tool

                // Check if mute state needs confirmation
                if (
                    defaultTool.isMuted != tool.isMuted ||
                    defaultTool.useWebGL != tool.useWebGL
                ) {
                    emitRequest(RequestRestartingRestore)
                    return@launch
                }
            }

            // No confirmation needed, apply defaults directly
            applyDefaultsByCategory(categories)
            _requestFromDefaults = null
        }
    }

    /**
     * Resets all settings to default values.
     *
     * This is a convenience method that delegates to [restoreDefaults] with all categories.
     */
    fun restoreDefaults() {
        restoreDefaults(SettingsCategory.All)
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is OnChangeTheme -> onChangeTheme(event)
            is OnChangeLanguage -> onChangeLanguage(event)
            RestoreDefaultSettings -> restoreDefaults()
            else -> {}
        }
    }

    override fun reject(request: SettingsRequest) {
        when (request) {
            is RequestRestartingRestore -> {
                _requestFromDefaults = null
            }

            else -> {}
        }
    }

    override fun resolve(request: SettingsRequest): Boolean {
        return when (request) {
            is RequestRestartingRestore -> {
                if (_requestFromDefaults != null) {
                    restoreDefaults(_requestFromDefaults!!)
                }
                _requestFromDefaults = null
                true
            }

            else -> false
        }
    }
}







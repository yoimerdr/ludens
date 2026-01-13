package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SystemSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsMode
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SettingsState as SettingsStateModel

/**
 * ViewModel for managing settings screen state and user interactions.
 *
 * @param getSettingsUseCase Use case for retrieving settings.
 * @param updateSettingsUseCase Use case for updating settings.
 */
@OptIn(FlowPreview::class)
@KoinViewModel
class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
) : ViewModel() {

    private val settings: SettingsStateModel
        get() = _state.value.settings

    private val _state = MutableStateFlow(SettingsState())

    /**
     * The current settings state exposed as a StateFlow.
     */
    val state: StateFlow<SettingsState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsState()
    )

    val systemState: StateFlow<SystemSettingsState> = _state.map {
        it.settings.system
    }.distinctUntilChanged().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SystemSettingsState()
        )

    val modeState: StateFlow<SettingsMode> = _state.map {
        it.mode
    }.distinctUntilChanged().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SettingsMode.Idle
        )

    /**
     * The original settings loaded from storage.
     */
    var sourceSettings: SettingsStateModel? = null
        private set

    private var _silenceValue: Boolean? = null
    private var _isSilenceFromDefaults: Boolean? = null

    init {
        viewModelScope.launch {
            getSettingsUseCase().collect { settings ->
                val model = settings.toUIModel()
                _state.update {
                    it.copy(
                        settings = model, mode = if (sourceSettings == null) SettingsMode.Idle
                        else it.mode
                    )
                }
                sourceSettings = model
            }
        }

        setupAutoupdateSettings()
    }

    /**
     * Sets up automatic persistence of settings changes with debouncing.
     */
    private fun setupAutoupdateSettings() {
        viewModelScope.launch {
            _state.map { it.settings.control }.autoupdateSettings {
                copy(control = it)
            }
        }
        viewModelScope.launch {
            _state.map { it.settings.tool }.autoupdateSettings {
                copy(tool = it)
            }
        }

        viewModelScope.launch {
            _state.map { it.settings.system }.autoupdateSettings {
                copy(system = it)
            }
        }
    }

    /**
     * Automatically persists settings changes with debouncing.
     *
     * @param transform Function to transform settings state.
     */
    private suspend fun <T> Flow<T>.autoupdateSettings(transform: SettingsStateModel.(T) -> SettingsStateModel) {
        this.distinctUntilChanged().debounce(400).collect {
            val updated = settings.transform(it)
            if (sourceSettings != null && updated != sourceSettings) {
                updateSettingsUseCase(
                    updated.toDomain()
                )
            }
        }
    }

    /**
     * Updates the settings state.
     *
     * @param transform Function to transform the state.
     */
    private fun updateState(transform: SettingsState.() -> SettingsState) {
        _state.update {
            transform(it)
        }
    }

    /**
     * Updates a control item.
     *
     * @param transform Function to transform the control item.
     */
    private fun updateControlsItem(transform: ControlItemState.() -> ControlItemState) {
        updateControls {
            copy(
                items = items.map(transform)
            )
        }
    }

    /**
     * Updates system settings.
     *
     * @param transform Function to transform system settings.
     */
    private fun updateSystem(transform: SystemSettingsState.() -> SystemSettingsState) {
        updateSettings {
            copy(system = transform(system))
        }
    }

    /**
     * Updates the settings model.
     *
     * @param transform Function to transform the settings model.
     */
    private fun updateSettings(transform: SettingsStateModel.() -> SettingsStateModel) {
        updateState {
            copy(
                settings = transform(settings)
            )
        }
    }


    /**
     * Updates tool settings.
     *
     * @param transform Function to transform tool settings.
     */
    private fun updateTools(transform: ToolSettingsState.() -> ToolSettingsState) {
        updateSettings {
            copy(tool = transform(tool))
        }
    }

    /**
     * Updates control settings.
     *
     * @param transform Function to transform control settings.
     */
    private fun updateControls(transform: ControlSettingsState.() -> ControlSettingsState) {
        updateSettings {
            copy(control = transform(control))
        }
    }

    /**
     * Updates the enabled state of all controls.
     *
     * @param event The event containing the enabled state.
     */
    fun updateControlsEnabled(event: SettingsEvent.UpdateControlsEnabled) {
        updateControls { copy(enabled = event.enabled) }
    }

    /**
     * Updates the alpha transparency of all controls.
     *
     * @param event The event containing the alpha value.
     */
    fun updateControlsAlpha(event: SettingsEvent.UpdateControlsAlpha) {
        updateControls {
            if (enabled) copy(
                alpha = event.alpha.coerceIn(ItemType.Joystick.toRange()),
                items = items.map { item ->
                    if (item.enabled) item.copy(
                        alpha = event.alpha.coerceIn(
                            item.type.toRange()
                        )
                    )
                    else item
                })
            else this
        }
    }

    /**
     * Updates the enabled state of a specific control.
     *
     * @param event The event containing the control type and enabled state.
     */
    fun updateControlEnabled(event: SettingsEvent.UpdateControlEnabled) {
        updateControlsItem {
            if (type == event.type) copy(enabled = event.enabled)
            else this
        }
    }

    /**
     * Updates the alpha transparency of a specific control.
     *
     * @param event The event containing the control type and alpha value.
     */
    fun updateControlAlpha(event: SettingsEvent.UpdateControlAlpha) {
        updateControlsItem {
            if ((type == ItemType.Settings && type == event.type) || (enabled && settings.control.enabled && type == event.type)) copy(
                alpha = event.alpha.coerceIn(
                    type.toRange()
                )
            )
            else this
        }
    }

    /**
     * Resolves the mute confirmation dialog.
     *
     * @param event The event containing whether to proceed with muting.
     */
    fun resolveMute(event: SettingsEvent.ResolveMuted) {
        if (_silenceValue == null) return

        if (event.proceed) {
            updateTools { copy(isMuted = _silenceValue!!) }

            if (_isSilenceFromDefaults == true) {
                updateState {
                    copy(settings = SettingsStateModel.default)
                }
            }
        }

        _silenceValue = null
        clearMode()
        _isSilenceFromDefaults = null
    }

    /**
     * Updates the mute state of the application.
     *
     * @param event The event containing the mute state.
     */
    fun requestMuteChange(event: SettingsEvent.UpdateAudioMuted) {
        _silenceValue = event.enabled
        updateState {
            copy(mode = SettingsMode.PendingMuteConfirmation)
        }
    }

    /**
     * Updates the FPS counter visibility.
     *
     * @param event The event containing the visibility state.
     */
    fun updateShowFps(event: SettingsEvent.UpdateShowFps) {
        updateTools { copy(showFPS = event.enabled) }
    }

    /**
     * Updates the position of a control.
     *
     * @param event The event containing the control type and position.
     */
    fun updateControlPosition(event: SettingsEvent.UpdateControlPosition) {
        updateSettings {
            copy(control = control.copy(positions = control.positions.map { item ->
                if (item.type == event.type) item.copy(x = event.x, y = event.y)
                else item
            }))
        }
    }

    /**
     * Toggles control movement mode.
     *
     * @param event The event containing whether to enable movement mode.
     */
    fun updateControlMovementMode(event: SettingsEvent.UpdateControlMovementMode) {
        updateMode(
            if (event.enabled) SettingsMode.MovableControls
            else SettingsMode.Idle
        )
    }

    /**
     * Updates the key binding for a control.
     *
     * @param event The event containing the control type and key.
     */
    fun updateControlKey(event: SettingsEvent.UpdateControlKey) {
        updateControlsItem {
            if (type == event.type && enabled && this is ControlKeyItemState) copy(
                key = event.key
            )
            else this
        }
    }

    /**
     * Resets all settings to default values.
     */
    fun restoreDefaultSettings() {
        val default = SettingsStateModel.default
        if (default.tool.isMuted != settings.tool.isMuted) {
            _isSilenceFromDefaults = true
            onEvent(SettingsEvent.UpdateAudioMuted(default.tool.isMuted))
        } else updateSettings { default }
    }

    /**
     * Selects a settings section.
     *
     * @param event The event containing the section to select.
     */
    fun onSelectSection(event: SettingsEvent.OnSelectSection) {
        updateState {
            copy(section = event.section)
        }
    }

    /**
     * Changes the application theme.
     *
     * @param event The event containing the theme to apply.
     */
    fun onChangeTheme(event: SettingsEvent.OnChangeTheme) {
        updateSystem {
            copy(
                theme = event.theme
            )
        }
    }

    /**
     * Changes the application language.
     *
     * @param event The event containing the language to apply.
     */
    fun onChangeLanguage(event: SettingsEvent.OnChangeLanguage) {
        updateSystem {
            copy(
                language = event.language
            )
        }

    }

    /**
     * Handles settings events.
     *
     * @param event The settings event to handle.
     */
    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateControlsEnabled -> updateControlsEnabled(event)
            is SettingsEvent.UpdateControlsAlpha -> updateControlsAlpha(event)
            is SettingsEvent.UpdateControlEnabled -> updateControlEnabled(event)
            is SettingsEvent.UpdateControlAlpha -> updateControlAlpha(event)
            is SettingsEvent.ResolveMuted -> resolveMute(event)
            is SettingsEvent.UpdateAudioMuted -> requestMuteChange(event)
            is SettingsEvent.UpdateShowFps -> updateShowFps(event)
            is SettingsEvent.UpdateControlPosition -> updateControlPosition(event)
            is SettingsEvent.UpdateControlMovementMode -> updateControlMovementMode(event)
            is SettingsEvent.UpdateControlKey -> updateControlKey(event)
            is SettingsEvent.RestoreDefaultSettings -> restoreDefaultSettings()
            is SettingsEvent.OnSelectSection -> onSelectSection(event)
            is SettingsEvent.OnChangeTheme -> onChangeTheme(event)
            is SettingsEvent.OnChangeLanguage -> onChangeLanguage(event)
            else -> {}
        }
    }

    /**
     * Updates the current mode.
     *
     * @param mode The mode to set.
     */
    private fun updateMode(mode: SettingsMode) {
        updateState {
            copy(
                mode = mode
            )
        }
    }

    /**
     * Resets the mode to Idle.
     */
    fun clearMode() {
        updateMode(SettingsMode.Idle)
    }

    /**
     * Whether the application requires a restart due to settings changes.
     */
    val requireRestart: Boolean
        get() {
            return settings.tool.isMuted != sourceSettings?.tool?.isMuted
        }
}

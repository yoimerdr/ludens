package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.extension.settings.default
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.SystemSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsCategory
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsMode
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsRequest
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsSection
import com.yoimerdr.compose.ludens.features.settings.presentation.state.SettingsState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    /**
     * Shared flow for emitting settings events to observers.
     */
    private val _events = MutableSharedFlow<SettingsEvent>()

    /**
     * Internal mutable state flow holding the current settings state.
     */
    private val _state = MutableStateFlow(SettingsState())

    /**
     * Flag indicating whether a settings request originated from restoring default settings.
     * Used to apply all default settings after a pending confirmation is resolved.
     * Contains the set of categories to reset, or null if no reset is in progress.
     */
    private var _requestFromDefaults: Set<SettingsCategory>? = null

    /**
     * Observable flow of settings events.
     */
    val events = _events.asSharedFlow()

    /**
     * The current settings state exposed as a StateFlow.
     */
    val state: StateFlow<SettingsState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsState(
            mode = SettingsMode.Idle
        )
    )

    /**
     * The current behavioral mode of the settings screen.
     */
    val modeState: StateFlow<SettingsMode> = _state.derivedStateIn(SettingsMode.Idle) { mode }

    /**
     * The currently selected settings section.
     */
    val sectionState: StateFlow<SettingsSection> =
        _state.derivedStateIn(SettingsSection.Controls) { section }

    /**
     * The system settings state (theme and language).
     */
    val systemState: StateFlow<SystemSettingsState> =
        _state.derivedStateIn(SystemSettingsState()) { settings.system }

    /**
     * The tool settings state (audio and FPS display).
     */
    val toolState: StateFlow<ToolSettingsState> =
        _state.derivedStateIn(ToolSettingsState()) { settings.tool }

    /**
     * The control settings state (on-screen controls configuration).
     */
    val controlState: StateFlow<ControlSettingsState> =
        _state.derivedStateIn(ControlSettingsState()) { settings.control }

    /**
     * The action settings state (quick action items).
     */
    val actionState: StateFlow<ActionSettingsState> =
        _state.derivedStateIn(ActionSettingsState()) { settings.action }


    /**
     * The original settings loaded from storage.
     */
    var sourceSettings: SettingsStateModel? = null
        private set

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
        setupEventsCollector()
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

    private fun setupEventsCollector() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is SettingsEvent.RequestRejected -> {
                        _requestFromDefaults = null
                    }

                    is SettingsEvent.RequestResolved -> {
                        if (it.success && _requestFromDefaults != null) {
                            restoreDefaultsByCategory(_requestFromDefaults!!)
                        }
                        _requestFromDefaults = null
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * Creates a derived StateFlow by mapping and caching a specific property from the source StateFlow.
     *
     * @param initialValue The initial value for the derived state.
     * @param mapper A function that extracts the desired property from the source state.
     */
    private fun <T, R> StateFlow<T>.derivedStateIn(
        initialValue: R,
        mapper: T.() -> R,
    ): StateFlow<R> {
        return map { it.mapper() }
            .distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = initialValue
            )
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

    private inline fun <T> PersistentList<T>.mutateIndexed(crossinline transform: MutableList<T>.(Int, T) -> Unit): PersistentList<T> {
        return mutate {
            it.forEachIndexed { index, item ->
                it.transform(index, item)
            }
        }
    }

    /**
     * Updates the alpha transparency of all controls.
     *
     * @param event The event containing the alpha value.
     */
    fun updateControlsAlpha(event: SettingsEvent.UpdateControlsAlpha) {
        updateControls {
            if (enabled) {
                val alpha = event.alpha.coerceIn(ItemType.Joystick.toRange())

                val items = items.toPersistentList().mutateIndexed { index, item ->
                    if (item.enabled) set(index, item.copy(alpha = alpha))
                }

                copy(
                    alpha = alpha,
                    items = items,
                )
            } else this
        }
    }

    /**
     * Updates the enabled state of a specific control.
     *
     * @param event The event containing the control index and enabled state.
     */
    fun updateControlEnabled(event: SettingsEvent.UpdateControlEnabled) {
        updateControls {
            if (enabled && event.index in items.indices) {
                val item = items[event.index]
                copy(
                    items = items.toPersistentList()
                        .set(event.index, item.copy(enabled = event.enabled))
                )
            } else this
        }
    }

    /**
     * Updates the alpha transparency of a specific control.
     *
     * @param event The event containing the control index and alpha value.
     */
    fun updateControlAlpha(event: SettingsEvent.UpdateControlAlpha) {
        updateControls {
            if (enabled && event.index in items.indices) {
                val item = items[event.index]
                val alpha = event.alpha.coerceIn(item.type.toRange())
                copy(
                    items = items.toPersistentList().set(event.index, item.copy(alpha = alpha))
                )
            } else this
        }
    }

    /**
     * Resolves the mute confirmation dialog.
     *
     * @param request The request containing the mute value.
     */
    private fun resolveMute(request: SettingsRequest.RequestMute): Boolean {
        updateTools {
            copy(isMuted = request.value)
        }

        return true
    }

    /**
     * Resolves the WebGL confirmation dialog.
     *
     * @param request The request containing the WebGL value.
     */
    private fun resolveWebGL(request: SettingsRequest.RequestWebGL): Boolean {
        updateTools {
            copy(useWebGL = request.value)
        }

        return true
    }

    private fun emitEvent(event: SettingsEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    /**
     * Updates the mute state of the application requesting a confirmation.
     *
     * @param event The event containing the mute state.
     */
    fun requestMuteChange(event: SettingsEvent.UpdateAudioMuted) {
        updateMode(
            SettingsMode.PendingConfirmation(
                SettingsRequest.RequestMute(
                    event.enabled
                )
            )
        )
    }

    /**
     * Updates the WebGL state of the application requesting a confirmation.
     *
     * @param event The event containing the WebGL state.
     */
    fun requestWebGLChange(event: SettingsEvent.UpdateUseWebGL) {
        updateMode(
            SettingsMode.PendingConfirmation(
                SettingsRequest.RequestWebGL(
                    event.enabled
                )
            )
        )
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
     * @param event The event containing the control index and position.
     */
    fun updateControlPosition(event: SettingsEvent.UpdateControlPosition) {
        updateControls {
            if (enabled && event.index in positions.indices) {
                val position = positions[event.index]
                copy(
                    positions = positions.toPersistentList()
                        .set(event.index, position.copy(x = event.x, y = event.y))
                )
            } else this
        }
    }

    fun restoreDefaultControlPositions(event: SettingsEvent.RestoreDefaultControlPositions) {
        updateControls {
            if (enabled) {
                copy(
                    positions = positions.toPersistentList()
                        .mutateIndexed { index, item ->
                            if (item.type in event.items) set(index, item.copy(x = 0f, y = 0f))
                        },
                )
            } else this
        }
    }

    fun swapPadPositions(event: SettingsEvent.SwapControlPositions) {
        updateControls {
            val (indices, bounds) = event
            val sourceIndex = indices.first
            val targetIndex = indices.second


            if (!enabled || sourceIndex == targetIndex || sourceIndex !in positions.indices || targetIndex !in positions.indices) this
            else {
                val source = positions[sourceIndex]
                val target = positions[targetIndex]

                val sourceX = bounds.left + source.x
                val sourceY = bounds.top + source.y

                val targetX = bounds.right + target.x
                val targetY = bounds.bottom + target.y

                val sourceOffsetX = targetX - bounds.left
                val sourceOffsetY = targetY - bounds.top

                val targetOffsetX = sourceX - bounds.right
                val targetOffsetY = sourceY - bounds.bottom

                copy(
                    positions = positions.toPersistentList()
                        .mutate {
                            it[sourceIndex] = source.copy(x = sourceOffsetX, y = sourceOffsetY)
                            it[targetIndex] = target.copy(x = targetOffsetX, y = targetOffsetY)
                        },
                )
            }

        }
    }

    /**
     * Updates the key binding for a control.
     *
     * @param event The event containing the control index and key.
     */
    fun updateControlKey(event: SettingsEvent.UpdateControlKey) {
        updateControls {
            if (enabled && event.index in items.indices) {
                val item = items[event.index]
                if (item.enabled && item is ControlKeyItemState) {
                    copy(
                        items = items.toPersistentList()
                            .set(event.index, item.copy(key = event.key))
                    )
                } else this
            } else this
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
    private fun applyDefaultsByCategory(categories: Set<SettingsCategory>) {
        val default = SettingsStateModel.default
        val categoriesToReset = categories.ifEmpty { SettingsCategory.All }

        updateSettings {
            var updated = this

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

            updated
        }
    }

    /**
     * Resets specific settings categories to their default values.
     *
     * @param categories The set of categories to reset. If empty, resets all categories.
     */
    fun restoreDefaultsByCategory(categories: Set<SettingsCategory>) {
        val default = SettingsStateModel.default
        val categories = categories.ifEmpty { SettingsCategory.All }

        // If already processing a request from defaults, apply the changes immediately
        if (_requestFromDefaults != null) {
            applyDefaultsByCategory(categories)
            return
        }

        // Check if Tools category needs to be reset and if it requires confirmation
        if (SettingsCategory.Tools in categories) {
            _requestFromDefaults = categories
            val defaultTool = default.tool
            val tool = settings.tool

            // Check if mute state needs confirmation
            if (defaultTool.isMuted != tool.isMuted) {
                onEvent(SettingsEvent.UpdateAudioMuted(defaultTool.isMuted))
                return
            }

            // Check if WebGL state needs confirmation
            if (defaultTool.useWebGL != tool.useWebGL) {
                onEvent(SettingsEvent.UpdateUseWebGL(defaultTool.useWebGL))
                return
            }
        }

        // No confirmation needed, apply defaults directly
        applyDefaultsByCategory(categories)
        _requestFromDefaults = null
    }

    /**
     * Resets all settings to default values.
     *
     * This is a convenience method that delegates to [restoreDefaultsByCategory] with all categories.
     */
    fun restoreDefaultSettings() {
        restoreDefaultsByCategory(SettingsCategory.All)
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
            is SettingsEvent.UpdateAudioMuted -> requestMuteChange(event)
            is SettingsEvent.UpdateShowFps -> updateShowFps(event)
            is SettingsEvent.UpdateUseWebGL -> requestWebGLChange(event)
            is SettingsEvent.UpdateControlPosition -> updateControlPosition(event)
            is SettingsEvent.RestoreDefaultControlPositions -> restoreDefaultControlPositions(event)
            is SettingsEvent.UpdateControlKey -> updateControlKey(event)
            is SettingsEvent.RestoreDefaultSettings -> restoreDefaultSettings()
            is SettingsEvent.RestoreDefaultsByCategory -> restoreDefaultsByCategory(event.categories)
            is SettingsEvent.OnSelectSection -> onSelectSection(event)
            is SettingsEvent.OnChangeTheme -> onChangeTheme(event)
            is SettingsEvent.OnChangeLanguage -> onChangeLanguage(event)
            is SettingsEvent.SwapControlPositions -> swapPadPositions(event)
            else -> {}
        }

        emitEvent(event)
    }

    /**
     * Rejects the current pending request and resets the mode to Idle.
     */
    fun rejectRequest() {
        clearMode()
        emitEvent(SettingsEvent.RequestRejected)
    }

    /**
     * Resolves the current pending request by applying the requested changes.
     *
     * After successful resolution, resets the mode to Idle.
     */
    fun resolveRequest() {
        when (val mode = _state.value.mode) {
            is SettingsMode.PendingConfirmation -> {
                val resolved = when (mode.request) {
                    is SettingsRequest.RequestMute -> resolveMute(mode.request)
                    is SettingsRequest.RequestWebGL -> resolveWebGL(mode.request)
                    else -> false
                }

                emitEvent(SettingsEvent.RequestResolved(resolved))
                if (resolved) {
                    clearMode()
                }
            }

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
            val tool = settings.tool
            val sourceTool = sourceSettings?.tool ?: return false
            return tool.isMuted != sourceTool.isMuted || tool.useWebGL != sourceTool.useWebGL
        }
}

package com.yoimerdr.compose.ludens.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateControlSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateToolSettingsUseCase
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.home.presentation.state.DebugFallbackIndexFilename
import com.yoimerdr.compose.ludens.features.home.presentation.state.EntryState
import com.yoimerdr.compose.ludens.features.home.presentation.state.FallbackIndexFilename
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeRequest
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeState
import com.yoimerdr.compose.ludens.features.home.presentation.state.IndexFilename
import com.yoimerdr.compose.ludens.features.home.presentation.state.RequestMute
import com.yoimerdr.compose.ludens.features.home.presentation.state.RequestWebGL
import com.yoimerdr.compose.ludens.ui.extensions.state.derivedStateIn
import com.yoimerdr.compose.ludens.ui.state.toQueryParameters
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ludens.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.MissingResourceException
import org.koin.android.annotation.KoinViewModel

/**
 * ViewModel for managing the home screen state and handling user interactions.
 *
 * This ViewModel coordinates game input events (joystick movements and control key presses)
 * and manages application settings. It automatically loads settings on initialization and
 * exposes them through a state flow for UI observation.
 *
 * @property getSettingsUseCase Use case for retrieving application settings
 * @property application Platform application instance for debug state checking
 * @property updateToolSettingsUseCase Use case for updating tool settings
 * @property updateControlSettingsUseCase Use case for updating control settings
 */
@KoinViewModel
class HomeViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val application: PlatformApplication,
    private val updateToolSettingsUseCase: UpdateToolSettingsUseCase,
    private val updateControlSettingsUseCase: UpdateControlSettingsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())

    private val _events = MutableSharedFlow<HomeEvent>()

    /**
     * Public shared flow exposing the joystick events.
     */
    val events: SharedFlow<HomeEvent>
        get() = _events.asSharedFlow()

    /**
     * Observable state flow containing the current home screen state.
     *
     * The state includes application settings, plugin information, and loading status.
     * This flow is shared while the UI is subscribed, with a 5-second timeout after
     * the last subscriber unsubscribes.
     */
    val state: StateFlow<HomeState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeState()
    )

    /**
     * Derived state indicating whether the application is loading initial data.
     *
     * This state automatically updates when the loading status in [state] changes.
     * Uses distinctUntilChanged internally (via derivedStateIn) to prevent unnecessary
     * recompositions when the loading state hasn't actually changed.
     */
    val loadingState: StateFlow<Boolean> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = true,
    ) { isLoading }

    /**
     * Derived state containing on-screen control settings.
     *
     * Exposes only the control-related portion of the settings state, allowing
     * UI components to subscribe to control changes without being affected by
     * other settings modifications. Optimized to avoid unnecessary updates.
     */
    val controlState: StateFlow<ControlSettingsState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = ControlSettingsState(),
    ) { settings.control }

    /**
     * Derived state containing action settings.
     *
     * Exposes only the action-related portion of the settings state.
     * Updates are deduplicated to optimize performance.
     */
    val actionsState: StateFlow<ActionSettingsState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = ActionSettingsState(),
    ) { settings.action }


    /**
     * Derived state containing tool settings (audio, FPS, WebGL).
     *
     * Provides access to tool-specific settings without exposing the entire
     * settings state. Updates are deduplicated to optimize performance.
     */
    val toolState: StateFlow<ToolSettingsState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = ToolSettingsState(),
    ) { settings.tool }

    /**
     * Derived state containing the game entry point URL and availability.
     *
     * Provides the URL to load the game and whether it's using the primary
     * or fallback index file. The URL includes query parameters based on
     * tool settings and web features.
     */
    val entryState: StateFlow<EntryState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = EntryState(
            url = Res.getUri(FallbackIndexFilename),
            isAvailable = false,
        ),
    ) { entry }

    init {
        // Load application settings and update state when they change
        viewModelScope.launch {
            getSettingsUseCase()
                .distinctUntilChanged()
                .collect { settings ->
                    updateState {
                        copy(
                            settings = settings.toUIModel(),
                            isLoading = false,
                        )
                    }
                }
        }
    }

    /**
     * Updates the internal state using a transformation function.
     *
     * @param update Lambda function that transforms the current [HomeState] to a new state
     */
    private inline fun updateState(crossinline update: HomeState.() -> HomeState) {
        _state.update {
            it.update()
        }
    }


    /**
     * Persists tool settings by applying a transformation and updating through the use case.
     *
     * @param transform A suspend function that transforms the current ToolSettingsState
     * to a new state with the desired changes
     */
    private suspend inline fun persistToolSettings(crossinline transform: suspend ToolSettingsState.() -> ToolSettingsState) {
        val settings = _state.value.settings.tool
        updateToolSettingsUseCase(transform(settings).toDomain())
    }

    /**
     * Resolves a request by executing an action and emitting a resolved event.
     *
     * @param request The request being resolved
     * @param action The suspend function to execute as part of the resolution
     */
    private inline fun resolveRequest(
        request: HomeRequest,
        crossinline action: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            action()
            _events.emit(HomeEvent.ResolvedRequest(request))
        }
    }

    /**
     * Handles home screen events and delegates them to appropriate handlers.
     *
     * @param event The home event to process
     */
    fun handle(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateEntry -> updateEntry(event)
            HomeEvent.ToggleFpsCounter -> toggleFpsCounter()
            HomeEvent.ToggleControlsVisibility -> toggleControlsVisibility()
            else -> {}
        }

        viewModelScope.launch {
            _events.emit(event)
        }
    }

    /**
     * Handles the update entry event.
     *
     * This method constructs the game entry URL with appropriate query parameters
     * based on the enabled features and settings. It handles fallback to alternative
     * index files when the primary one is not available.
     *
     * @param event The event containing tool settings and web features
     */
    fun updateEntry(event: HomeEvent.UpdateEntry) {
        val toolSettings = _state.value.settings.tool
        val webFeatures = event.features

        val features = webFeatures.copy(
            supportsWebAudio = if (webFeatures.supportsWebAudio)
                !toolSettings.isMuted
            else false,
            supportsWebGL = webFeatures.supportsWebGL && toolSettings.useWebGL
        )

        val parameters = features.toQueryParameters()
        val result = try {
            Res.getUri(IndexFilename) + "?$parameters" to true
        } catch (_: MissingResourceException) {
            Res.getUri(
                if (application.isDebug) DebugFallbackIndexFilename
                else FallbackIndexFilename
            ) to false
        }

        val entry = EntryState(
            url = result.first,
            isAvailable = result.second,
        )

        updateState {
            copy(entry = entry)
        }
    }

    /**
     * Handles toggling the FPS counter visibility.
     *
     * Toggles the FPS counter setting based on the current value,
     * then persists the change through the use case.
     */
    fun toggleFpsCounter() {
        viewModelScope.launch {
            persistToolSettings {
                copy(showFPS = !showFPS)
            }
        }
    }

    /**
     * Handles toggling the on-screen controls visibility.
     *
     * Toggles the control visibility setting based on the current value,
     * then persists the change through the use case.
     */
    fun toggleControlsVisibility() {
        viewModelScope.launch {
            val value = _state.value
            val settings = value.settings.control

            updateControlSettingsUseCase(
                settings
                    .copy(enabled = !settings.enabled)
                    .toDomain(),
            )
        }
    }

    /**
     * Resolves a mute request by updating the mute setting.
     *
     * @param request The mute request containing the desired enabled state
     */
    fun resolveMuteRequest(request: RequestMute) {
        resolveRequest(request) {
            persistToolSettings {
                copy(isMuted = request.enabled)
            }
        }
    }

    /**
     * Resolves a WebGL request by updating the WebGL setting.
     *
     * @param request The WebGL request containing the desired enabled state
     */
    fun resolveWebGLRequest(request: RequestWebGL) {
        resolveRequest(request) {
            persistToolSettings {
                copy(useWebGL = request.enabled)
            }
        }
    }

    /**
     * Rejects a request by emitting a rejected event without action.
     *
     * @param request The request to reject
     */
    fun reject(request: HomeRequest) {
        viewModelScope.launch {
            _events.emit(HomeEvent.RejectedRequest(request))
        }
    }

    /**
     * Resolves a request based on its type.
     *
     * @param request The request to resolve, which will be handled according to its type
     */
    fun resolve(request: HomeRequest) {
        when (request) {
            is RequestMute -> resolveMuteRequest(request)
            is RequestWebGL -> resolveWebGLRequest(request)
        }
    }
}
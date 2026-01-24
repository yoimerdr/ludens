package com.yoimerdr.compose.ludens.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetSettingsUseCase
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toEvent
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.down
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.downLeft
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.downRight
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.left
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.none
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.right
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.up
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.upLeft
import com.yoimerdr.compose.ludens.core.infrastructure.extension.player.upRight
import com.yoimerdr.compose.ludens.core.infrastructure.platform.PlatformApplication
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.home.presentation.state.DebugFallbackIndexFilename
import com.yoimerdr.compose.ludens.features.home.presentation.state.EntryState
import com.yoimerdr.compose.ludens.features.home.presentation.state.FallbackIndexFilename
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeState
import com.yoimerdr.compose.ludens.features.home.presentation.state.IndexFilename
import com.yoimerdr.compose.ludens.ui.extensions.state.derivedStateIn
import com.yoimerdr.compose.ludens.ui.state.PluginState
import com.yoimerdr.compose.ludens.ui.state.WebFeaturesState
import com.yoimerdr.compose.ludens.ui.state.isAvailable
import com.yoimerdr.compose.ludens.ui.state.toQueryParameters
import io.github.yoimerdr.compose.virtualjoystick.core.control.Direction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
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
 * @property getSettings Use case for retrieving application settings
 */
@KoinViewModel
class HomeViewModel(
    private val getSettings: GetSettingsUseCase,
    private val application: PlatformApplication,
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
     * Derived state containing web platform feature support information.
     *
     * Indicates which web features (WebAudio, WebGL, etc.) are supported
     * by the current platform. Used to determine entry URL parameters.
     */
    val featuresState: StateFlow<WebFeaturesState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = WebFeaturesState(),
    ) { features }

    /**
     * Derived state containing plugin loading and availability status.
     *
     * Monitors the YDP_Ludens plugin state, including whether it's loading
     * or available for use. Controls are only shown when the plugin is available.
     */
    val pluginState: StateFlow<PluginState> = _state.derivedStateIn(
        scope = viewModelScope,
        initialValue = PluginState(),
    ) { plugin }

    /**
     * Derived state containing the game entry point URL and availability.
     *
     * Provides the URL to load the game and whether it's using the primary
     * or fallback index file. Automatically updates when tool settings or
     * web features change.
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
            getSettings().collect { settings ->
                updateState {
                    copy(
                        settings = settings.toUIModel(),
                        isLoading = false,
                    )
                }
            }
        }

        // Update entry URL when dependencies change
        viewModelScope.launch {
            combine(
                toolState,
                featuresState,
                loadingState
            ) { tools, features, loading ->
                Triple(tools, features, loading)
            }.collect { (tools, features, loading) ->
                if (!loading) {
                    updateEntry(tools, features)
                }
            }
        }
    }

    /**
     * Updates the entry state based on tool settings and web features.
     *
     * This method constructs the game entry URL with appropriate query parameters
     * based on the enabled features and settings. It handles fallback to alternative
     * index files when the primary one is not available.
     *
     * @param toolSettings The tool settings containing audio and WebGL preferences
     * @param webFeatures The web features supported by the platform
     */
    fun updateEntry(
        toolSettings: ToolSettingsState,
        webFeatures: WebFeaturesState,
    ) {
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
     * Updates the internal state using a transformation function.
     *
     * @param update Lambda function that transforms the current [HomeState] to a new state
     */
    fun updateState(update: HomeState.() -> HomeState) {
        _state.update {
            it.update()
        }
    }

    /**
     * Handles home screen events and delegates them to appropriate handlers.
     *
     * @param event The home event to process
     */
    fun handle(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnPluginLoaded -> onPluginLoaded(event)
            is HomeEvent.OnWebFeaturesLoaded -> onWebFeaturesLoaded(event)
            is HomeEvent.ToggleFpsCounter -> toggleFpsCounter(event)
            is HomeEvent.OnPlayerMovement -> onPlayerMovement(event)
            is HomeEvent.OnClickControlKey -> onControlKeyClick(event)
            else -> {}
        }

        viewModelScope.launch {
            _events.emit(event)
        }
    }

    /**
     * Handles the plugin loaded event.
     *
     * @param event The event containing the plugin state
     */
    fun onPluginLoaded(event: HomeEvent.OnPluginLoaded) {
        updateState { copy(plugin = event.plugin) }
    }

    /**
     * Handles the web features loaded event.
     *
     * @param event The event containing the web features
     */
    fun onWebFeaturesLoaded(event: HomeEvent.OnWebFeaturesLoaded) {
        updateState { copy(features = event.features) }
    }

    /**
     * Handles toggling the FPS counter visibility.
     *
     * @param event The event containing the FPS player instance
     */
    fun toggleFpsCounter(event: HomeEvent.ToggleFpsCounter) {
        val state = _state.value
        if (!state.plugin.isAvailable) return

        val player = event.player
        if (state.settings.tool.showFPS) {
            player.show()
        } else {
            player.hide()
        }
    }

    /**
     * Handles player movement via virtual joystick.
     *
     * @param event The player movement event containing player and movement data
     */
    fun onPlayerMovement(event: HomeEvent.OnPlayerMovement) {
        val player = event.player
        when (event.snapshot.direction) {
            Direction.Up -> player.up(event.type)
            Direction.Down -> player.down(event.type)
            Direction.Left -> player.left(event.type)
            Direction.Right -> player.right(event.type)
            Direction.UpLeft -> player.upLeft(event.type)
            Direction.UpRight -> player.upRight(event.type)
            Direction.DownLeft -> player.downLeft(event.type)
            Direction.DownRight -> player.downRight(event.type)
            else -> player.none()
        }
    }

    /**
     * Handles control key button click events.
     *
     * @param event The control key click event
     */
    fun onControlKeyClick(event: HomeEvent.OnClickControlKey) {
        event.player.onKeyEvent(
            event.key.toEvent(type = event.type)
        )
    }
}
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
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeEvent
import com.yoimerdr.compose.ludens.features.home.presentation.state.HomeState
import com.yoimerdr.compose.ludens.ui.state.isAvailable
import io.github.yoimerdr.compose.virtualjoystick.core.control.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())

    /**
     * Observable state flow containing the current home screen state.
     *
     * The state includes application settings, plugin information, and loading status.
     * This flow is shared while the UI is subscribed, with a 5-second timeout after
     * the last subscriber unsubscribes.
     */
    val state: StateFlow<HomeState> = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeState()
        )

    init {
        // Load application settings and update state when they change
        viewModelScope.launch {
            getSettings().collect { settings ->
                updateState {
                    copy(
                        settings = settings.toUIModel(),
                        isLoading = false
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
    private fun updateState(update: HomeState.() -> HomeState) {
        _state.update {
            it.update()
        }
    }

    /**
     * Handles home screen events and delegates them to appropriate handlers.
     *
     * Processes types:
     * - [HomeEvent.OnPlayerMovement]: Virtual joystick movements, translating directional
     *   input (up, down, left, right, and diagonals) into player movement commands
     * - [HomeEvent.OnClickControlKey]: Action button presses, forwarding key events to the
     *   input player for game control
     *
     * @param event The home event to process
     */
    fun onEvent(event: HomeEvent) {
        val value = _state.value
        val settings = value.settings

        when (event) {
            is HomeEvent.OnPluginLoaded -> {
                updateState { copy(plugin = event.plugin) }
            }

            is HomeEvent.ToggleFpsCounter -> {
                if (!value.plugin.isAvailable)
                    return
                if (settings.tool.showFPS)
                    event.player.show()
                else event.player.hide()
            }

            is HomeEvent.OnPlayerMovement -> {
                val player = event.player
                val snapshot = event.snapshot
                val type = event.type

                when (snapshot.direction) {
                    Direction.Up -> player.up(type)
                    Direction.Down -> player.down(type)
                    Direction.Left -> player.left(type)
                    Direction.Right -> player.right(type)
                    Direction.UpLeft -> player.upLeft(type)
                    Direction.UpRight -> player.upRight(type)
                    Direction.DownLeft -> player.downLeft(type)
                    Direction.DownRight -> player.downRight(type)
                    else -> player.none()
                }
            }

            is HomeEvent.OnClickControlKey -> {
                event.player.onKeyEvent(
                    event.key.toEvent(type = event.type)
                )
            }

            else -> {}
        }
    }

}
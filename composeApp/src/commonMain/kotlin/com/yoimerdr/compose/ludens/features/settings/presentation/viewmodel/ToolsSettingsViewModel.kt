package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetToolSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateToolSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ToolSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateAudioMuted
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateShowFps
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateUseWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestMute
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.RequestWebGL
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.SettingsRequest
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * View model for managing tool settings section.
 *
 * This view model handles the state and events for application tools.
 *
 * The view model automatically loads settings from the data source and persists
 * changes with debouncing to prevent excessive write operations.
 *
 * @param getSettingsUseCase Use case for retrieving tool settings.
 * @param updateSettingsUseCase Use case for persisting tool settings.
 */
@KoinViewModel
class ToolsSettingsViewModel(
    private val getSettingsUseCase: GetToolSettingsUseCase,
    private val updateSettingsUseCase: UpdateToolSettingsUseCase,
) : BaseSettingsViewModel<ToolSettingsState>(
    initialValue = ToolSettingsState(),
) {

    init {
        viewModelScope.launch {
            getSettingsUseCase().stateCollect { settings ->
                settings.toUIModel()
            }
        }

        viewModelScope.launch {
            persistCollect {
                updateSettingsUseCase(
                    it.toDomain(),
                )
            }
        }
    }

    /**
     * Updates the mute state of the application.
     *
     * @param event The event containing the mute state.
     */
    fun updateAudioMuted(event: UpdateAudioMuted) {
        updateState { copy(isMuted = event.enabled) }
    }

    /**
     * Updates the WebGL state of the application.
     *
     * @param event The event containing the WebGL state.
     */
    fun updateUseWebGL(event: UpdateUseWebGL) {
        updateState { copy(useWebGL = event.enabled) }
    }

    /**
     * Updates the FPS counter visibility.
     *
     * @param event The event containing the visibility state.
     */
    fun updateShowFps(event: UpdateShowFps) {
        updateState { copy(showFPS = event.enabled) }
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is UpdateShowFps -> updateShowFps(event)
            is SettingsEvent.TryUpdate -> {
                when (event.event) {
                    is UpdateAudioMuted -> emitRequest(
                        RequestMute(event.event.enabled)
                    )

                    is UpdateUseWebGL -> emitRequest(
                        RequestWebGL(event.event.enabled)
                    )

                    else -> {}
                }
            }

            is UpdateAudioMuted -> {
                updateAudioMuted(event)
            }

            is UpdateUseWebGL -> {
                updateUseWebGL(event)
            }

            else -> {}
        }
    }

    override fun resolve(request: SettingsRequest): Boolean {
        return when (request) {
            is RequestMute -> {
                handle(
                    UpdateAudioMuted(
                        request.value
                    )
                )
                true
            }

            is RequestWebGL -> {
                handle(
                    UpdateUseWebGL(
                        request.value,
                    )
                )
                true
            }

            else -> false
        }
    }
}


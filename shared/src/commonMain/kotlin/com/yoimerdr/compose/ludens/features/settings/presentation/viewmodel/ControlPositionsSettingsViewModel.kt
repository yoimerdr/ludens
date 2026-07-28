package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.usecase.GetControlSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateControlSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.RestoreDefaultControlPositions
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SwapControlPositions
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlPosition
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * View model for managing control positions settings section.
 *
 * This view model handles the state and events for on-screen game controls.
 *
 * The view model automatically loads settings from the data source and persists
 * changes with debouncing to prevent excessive write operations.
 *
 * @param getSettingsUseCase Use case for retrieving control settings.
 * @param updateSettingsUseCase Use case for persisting control settings.
 */
@KoinViewModel
class ControlPositionsSettingsViewModel(
    private val getSettingsUseCase: GetControlSettingsUseCase,
    private val updateSettingsUseCase: UpdateControlSettingsUseCase,
) : BaseSettingsViewModel<ControlSettingsState>(
    initialValue = ControlSettingsState(),
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
     * Updates the position of a control.
     *
     * @param event The event containing the control index and position.
     */
    fun updateControlPosition(event: UpdateControlPosition) {
        updateState {
            if (enabled && event.index in positions.indices) {
                val position = positions[event.index]
                copy(
                    positions = positions.toPersistentList()
                        .set(event.index, position.copy(x = event.x, y = event.y))
                )
            } else this
        }
    }

    fun restoreDefaultControlPositions(event: RestoreDefaultControlPositions) {
        updateState {
            if (enabled) {
                copy(
                    positions = positions.toPersistentList()
                        .mutate {
                            for (index in it.indices) {
                                val item = it[index]
                                if (item.type in event.items)
                                    it[index] = item.copy(x = 0f, y = 0f)
                            }
                        },
                )
            } else this
        }
    }

    fun swapControlPositions(event: SwapControlPositions) {
        updateState {
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

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is UpdateControlPosition -> updateControlPosition(event)
            is RestoreDefaultControlPositions -> restoreDefaultControlPositions(event)
            is SwapControlPositions -> swapControlPositions(event)
            else -> {}
        }
    }
}


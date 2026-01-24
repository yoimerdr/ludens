package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.model.settings.ActionType
import com.yoimerdr.compose.ludens.core.domain.usecase.GetActionSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateActionSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ActionSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SwapActionOrder
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateActionEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateActionsEnabled
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * View model for managing action settings section.
 *
 * This view model handles the state and events for quick action items.
 *
 * The view model automatically loads settings from the data source and persists
 * changes with debouncing to prevent excessive write operations.
 *
 * @param getSettingsUseCase Use case for retrieving action settings.
 * @param updateSettingsUseCase Use case for persisting action settings.
 */
@KoinViewModel
class ActionSettingsViewModel(
    private val getSettingsUseCase: GetActionSettingsUseCase,
    private val updateSettingsUseCase: UpdateActionSettingsUseCase,
) : BaseSettingsViewModel<ActionSettingsState>(
    initialValue = ActionSettingsState(),
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
     * Updates the enabled state of all action items.
     *
     * @param event The event containing the enabled state.
     */
    fun updateActionsEnabled(event: UpdateActionsEnabled) {
        updateState { copy(enabled = event.enabled) }
    }

    /**
     * Swaps the order of two action items.
     *
     * @param event The event containing the indices of the items to swap.
     */
    fun swapActionOrder(event: SwapActionOrder) {
        updateState {
            val sourceIndex = event.indices.first
            val targetIndex = event.indices.second
            val indices = items.indices
            if (enabled && sourceIndex in indices && targetIndex in indices) {
                val source = items[sourceIndex]
                val target = items[targetIndex]

                val items = items.toPersistentList().mutate {
                    it[sourceIndex] = source.copy(order = target.order)
                    it[targetIndex] = target.copy(order = source.order)
                    it.sortBy { item -> item.order }
                }

                copy(items = items)
            } else this
        }
    }

    /**
     * Updates the enabled state of a specific action item.
     *
     * @param event The event containing the action index and enabled state.
     */
    fun updateActionEnabled(event: UpdateActionEnabled) {
        updateState {
            val source = items.getOrNull(event.index)
            if (enabled && source != null && source.type != ActionType.Settings) {
                val (enabled, disabled) = items.partition { it.enabled }

                val last = if (event.enabled) enabled.lastOrNull() ?: disabled.first()
                else disabled.lastOrNull() ?: enabled.last()

                val items = items.toPersistentList().mutate {
                    it[event.index] = source.copy(
                        enabled = event.enabled,
                        order = last.order + 1,
                    )
                    it.sortBy { item -> item.order }
                }

                copy(items = items)
            } else this
        }
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is UpdateActionsEnabled -> updateActionsEnabled(event)
            is SwapActionOrder -> swapActionOrder(event)
            is UpdateActionEnabled -> updateActionEnabled(event)
            else -> {}
        }
    }
}


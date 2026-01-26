package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.core.domain.model.settings.ItemType
import com.yoimerdr.compose.ludens.core.domain.usecase.GetControlSettingsUseCase
import com.yoimerdr.compose.ludens.core.domain.usecase.UpdateControlSettingsUseCase
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toDomain
import com.yoimerdr.compose.ludens.core.presentation.mapper.settings.toUIModel
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlKeyItemState
import com.yoimerdr.compose.ludens.core.presentation.model.settings.ControlSettingsState
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlAlpha
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlEnabled
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlKey
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlsAlpha
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.UpdateControlsEnabled
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * View model for managing control settings section.
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
class ControlsSettingsViewModel(
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
     * Updates a specific enabled control item.
     *
     *
     * @param index The index of the control to update.
     * @param transform Function to transform the control item state.
     */
    private inline fun updateControl(
        index: Int,
        crossinline transform: ControlItemState.() -> ControlItemState,
    ) {
        updateState {
            val item = items.getOrNull(index)
            if (enabled && item != null && item.enabled) {
                copy(
                    items = items.toPersistentList().set(index, item.transform())
                )
            } else this
        }
    }

    /**
     * Updates the enabled state of all controls.
     *
     * @param event The event containing the enabled state.
     */
    fun updateControlsEnabled(event: UpdateControlsEnabled) {
        updateState { copy(enabled = event.enabled) }
    }

    /**
     * Updates the alpha transparency of all controls.
     *
     * @param event The event containing the alpha value.
     */
    fun updateControlsAlpha(event: UpdateControlsAlpha) {
        updateState {
            if (enabled) {
                val alpha = event.alpha.coerceIn(ItemType.Joystick.toRange())

                val items = items.toPersistentList().mutate {
                    for (index in it.indices) {
                        it[index] = it[index].copy(alpha = alpha)
                    }
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
    fun updateControlEnabled(event: UpdateControlEnabled) {
        updateControl(event.index) {
            copy(enabled = event.enabled)
        }
    }

    /**
     * Updates the alpha transparency of a specific control.
     *
     * @param event The event containing the control index and alpha value.
     */
    fun updateControlAlpha(event: UpdateControlAlpha) {
        updateControl(event.index) {
            val alpha = event.alpha.coerceIn(type.toRange())
            copy(alpha = alpha)
        }
    }

    /**
     * Updates the key binding for a control.
     *
     * @param event The event containing the control index and key.
     */
    fun updateControlKey(event: UpdateControlKey) {
        updateControl(event.index) {
            if (this is ControlKeyItemState) copy(key = event.key)
            else this
        }
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is UpdateControlsEnabled -> updateControlsEnabled(event)
            is UpdateControlsAlpha -> updateControlsAlpha(event)
            is UpdateControlEnabled -> updateControlEnabled(event)
            is UpdateControlAlpha -> updateControlAlpha(event)
            is UpdateControlKey -> updateControlKey(event)
            else -> {}
        }
    }

}
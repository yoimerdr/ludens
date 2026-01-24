package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.ui.extensions.state.debounceCollect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base view model for settings sections that manages state and events.
 *
 * @param State The type of the settings state.
 * @param initialValue The initial value for the settings state.
 * @param stateStarted The strategy for starting and stopping state flow collection.
 */
abstract class BaseSettingsViewModel<State>(
    initialValue: State,
    stateStarted: SharingStarted = SharingStarted.WhileSubscribed(5000L),
) : ViewModel() {

    /**
     * Mutable state flow holding the current settings state.
     */
    protected val mState = MutableStateFlow(initialValue)

    /**
     * Shared flow for emitting settings events.
     */
    protected val mEvents = MutableSharedFlow<SettingsEvent>()

    /**
     * Stores the source settings to track changes and determine when to persist updates.
     * When null, persistence is disabled.
     */
    protected var sourceSettings: State? = null

    /**
     * Indicates whether the settings are currently loading from source.
     */
    var isLoading by mutableStateOf(true)
        protected set

    /**
     * State flow exposing the current settings state.
     */
    val state = mState.stateIn(
        scope = viewModelScope, started = stateStarted, initialValue = initialValue
    )

    val events = mEvents.asSharedFlow()

    /**
     * Provides access to the current settings state value.
     */
    protected val settings: State
        get() = mState.value


    /**
     * Collects a flow and transforms each emission into a settings state.
     * Updates the loading state and tracks the source settings.
     *
     * @param T The type of values emitted by the flow.
     * @param transform Function to transform flow emissions into settings state.
     */
    protected suspend inline fun <T> Flow<T>.stateCollect(crossinline transform: (T) -> State) {
        distinctUntilChanged().collect { source ->
            updateState {
                val result = transform(source)
                if (sourceSettings == null) {
                    sourceSettings = result
                }
                isLoading = false
                result
            }
        }
    }

    /**
     * Collects state changes with debouncing and executes persistence logic.
     * Only persists changes that differ from the source settings.
     *
     * @param executor Function to execute for persisting the state.
     */
    protected suspend inline fun persistCollect(crossinline executor: suspend (State) -> Unit) {
        mState.map { it }
            .debounceCollect {
                if (sourceSettings != null) {
                    executor(it)
                }
            }
    }

    /**
     * Updates the settings state.
     *
     * @param transform Function to transform the state.
     */
    protected inline fun updateState(crossinline transform: State.() -> State) {
        mState.update {
            transform(it)
        }
    }

    /**
     * Emits a settings event through the shared flow.
     *
     * @param event The event to emit.
     */
    protected fun emitEvent(event: SettingsEvent) {
        viewModelScope.launch {
            mEvents.emit(event)
        }
    }

    /**
     * Handles a settings event.
     * Subclasses must implement this method to define event handling logic.
     *
     * @param event The event to handle.
     */
    protected abstract fun onEvent(event: SettingsEvent)

    /**
     * Handles and emits a settings event.
     *
     * @param event The event to handle and emit.
     */
    fun handle(event: SettingsEvent) {
        onEvent(event)

        emitEvent(event)
    }
}

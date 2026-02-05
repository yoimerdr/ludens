package com.yoimerdr.compose.ludens.features.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoimerdr.compose.ludens.features.settings.presentation.state.events.SettingsEvent
import com.yoimerdr.compose.ludens.features.settings.presentation.state.requests.SettingsRequest
import com.yoimerdr.compose.ludens.ui.extensions.state.debounceCollect
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
     * Mutable state flow holding the source settings state.
     *
     * This value must mutate only when loading from source.
     * */
    protected val mSource = MutableStateFlow<State?>(null)

    /**
     * Shared flow for emitting settings events.
     */
    protected val mEvents = MutableSharedFlow<SettingsEvent>()

    /**
     * Shared flow for emitting settings requests.
     * */
    protected val mRequests = MutableSharedFlow<SettingsRequest>()

    /**
     * Indicates whether the settings are currently loading from source.
     */
    var isLoading by mutableStateOf(true)
        protected set

    /**
     * State flow exposing the current settings state.
     */
    open val state = mState.stateIn(
        initialValue = initialValue,
        stateStarted = stateStarted
    )

    /**
     * State flow exposing the source settings state.
     * */
    open val source = mSource.stateIn(
        initialValue = null,
        stateStarted = stateStarted
    )

    /**
     * Shared flow exposing settings events.
     * */
    val events = mEvents.asSharedFlow()

    /**
     * Shared flow exposing settings requests.
     * */
    val requests = mRequests.asSharedFlow()

    /**
     * Provides access to the source settings state value.
     * */
    protected open val sourceSettings: State?
        get() = mSource.value

    /**
     * Provides access to the current settings state value.
     */
    protected open val settings: State
        get() = mState.value

    /**
     * Converts a flow into a state flow within the view model scope.
     *
     * @see kotlinx.coroutines.flow.stateIn
     * */
    protected fun <T> Flow<T>.stateIn(
        initialValue: T,
        stateStarted: SharingStarted = SharingStarted.WhileSubscribed(5000L),
    ) = this.stateIn(
        scope = viewModelScope,
        started = stateStarted,
        initialValue = initialValue
    )

    /**
     * Collects a flow and transforms each emission into a settings state.
     * Updates the loading state and tracks the source settings.
     *
     * @param T The type of values emitted by the flow.
     * @param transform Function to transform flow emissions into settings state.
     */
    protected suspend inline fun <T> Flow<T>.stateCollect(crossinline transform: (T) -> State) {
        distinctUntilChanged().collect { source ->
            val result = transform(source)
            updateState {
                result
            }

            if (mSource.value == null) {
                mSource.update { result }
            }

            isLoading = false
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
                val source = sourceSettings
                if (source != null && it != source) {
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
     * Emits a settings request through the shared flow.
     *
     * @param request The request to emit.
     * */
    protected fun emitRequest(request: SettingsRequest) {
        viewModelScope.launch {
            mRequests.emit(request)
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

    open fun resolve(request: SettingsRequest): Boolean = false

    open fun reject(request: SettingsRequest) {}

    protected open fun reset(
        initialValue: State,
    ) {
        mSource.update { null }
        isLoading = true
        mState.update { initialValue }
    }
}

package com.yoimerdr.compose.ludens.ui.extensions.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Creates a derived [StateFlow] by mapping and caching a specific property from the source [StateFlow].
 *
 * This extension function transforms a source StateFlow into a new StateFlow by applying a mapping
 * function to extract or compute a derived value. The resulting StateFlow only emits when the mapped
 * value changes (using [distinctUntilChanged]), reducing unnecessary updates to collectors.
 *
 * This is particularly useful for:
 * - Exposing specific properties from a complex state object
 * - Computing derived values that depend on the source state
 * - Optimizing performance by avoiding recomposition when irrelevant state changes
 *
 * @param scope The [CoroutineScope] in which the derived StateFlow will be active.
 * @param initialValue The initial value for the derived state, used before the first value is computed.
 * @param started The strategy for starting and stopping the sharing of the StateFlow.
 * Defaults to [SharingStarted.WhileSubscribed] with a 5-second timeout to optimize resource usage.
 * @param mapper A function that transforms the source state into the derived state.
 * Invoked as an extension function on the source state.
 * @return A new [StateFlow] that emits the mapped values, distinct until changed.
 *
 * @see StateFlow
 * @see distinctUntilChanged
 * @see stateIn
 */
fun <T, R> StateFlow<T>.derivedStateIn(
    scope: CoroutineScope,
    initialValue: R,
    started: SharingStarted = SharingStarted.WhileSubscribed(5000),
    mapper: T.() -> R,
): StateFlow<R> {
    return map(mapper)
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = started,
            initialValue = initialValue
        )
}

/**
 * Collects values from a [Flow] with debouncing and deduplication.
 *
 * This suspend function applies two important transformations before collecting:
 * 1. [distinctUntilChanged] - Filters out consecutive duplicate values
 * 2. [debounce] - Delays emissions until the specified time has passed without new values
 *
 * This is particularly useful for:
 * - Handling rapid user input (e.g., search queries, text fields)
 * - Reducing API calls triggered by frequent state changes
 * - Optimizing performance by preventing excessive processing of similar values
 *
 * The debouncing occurs after deduplication, ensuring that only distinct values are
 * considered for the debounce timeout.
 *
 * @param debounceMillis The time in milliseconds to wait for silence before emitting a value.
 * Defaults to 400ms, which is a common value for search-as-you-type functionality.
 * @param collector The [FlowCollector] that will receive the debounced, distinct values.
 *
 * @see distinctUntilChanged
 * @see debounce
 * @see Flow.collect
 * @see FlowCollector
 */
@OptIn(FlowPreview::class)
suspend fun <T> Flow<T>.debounceCollect(
    debounceMillis: Long = 400,
    collector: FlowCollector<T>,
) {
    this.distinctUntilChanged()
        .debounce(debounceMillis)
        .collect(collector)
}
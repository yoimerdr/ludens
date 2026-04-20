package com.yoimerdr.compose.ludens.ui.components.interaction

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Represents a request for user interaction.
 *
 * This interface serves as a marker for all types of interaction requests
 * that can be made through the [InteractionManager]. Implementations should
 * define specific interaction types (e.g., confirmation dialogs, input prompts).
 */
interface InteractionRequest

/**
 * Represents the result of an interaction request.
 *
 * This interface defines the contract for all interaction results,
 * which must be associated with their originating [request].
 *
 * @property request The original interaction request that produced this result.
 */
interface InteractionResult {
    val request: InteractionRequest
}

/**
 * Represents a rejected interaction request.
 *
 * This result is used when a user cancels or dismisses an interaction
 * without providing a value.
 *
 * @property request The original interaction request that was rejected.
 * @property reason Optional reason for the rejection (e.g., "user_cancelled", "timeout").
 */
data class InteractionRejected(
    override val request: InteractionRequest,
    val reason: String?,
) : InteractionResult

/**
 * Represents a successfully resolved interaction request.
 *
 * This result is used when a user completes an interaction and provides a value.
 *
 * @property request The original interaction request that was resolved.
 * @property value The value provided by the user, or null if no value was provided.
 */
data class InteractionResolved<T>(
    override val request: InteractionRequest,
    val value: T?,
) : InteractionResult

/**
 * Manages user interaction requests and their results in a thread-safe manner.
 *
 * This interface provides a mechanism for requesting user interactions (such as dialogs,
 * confirmations, or input prompts) and receiving the results asynchronously through
 * reactive state flows.
 *
 * Typical usage:
 * 1. Call [request] to initiate an interaction request.
 * 2. Observe [request] to display the appropriate UI.
 * 3. Call [resolve] with the result when the user completes the interaction.
 * 4. Observe [result] to handle the interaction result.
 *
 */
interface InteractionManager {
    /**
     * A [StateFlow] emitting the current pending interaction request, or null if no request is pending.
     * */
    val request: StateFlow<InteractionRequest?>

    /**
     * A [StateFlow] emitting the result of the last resolved interaction, or null if no result is available.
     * */
    val result: StateFlow<InteractionResult?>

    /**
     * Requests a new user interaction.
     *
     * This method clears any previous result and sets the new request.
     * Subsequent observers of [request] will be notified of the new interaction.
     *
     * @param request The interaction request to be processed.
     */
    suspend fun request(request: InteractionRequest)

    /**
     * Resolves the current interaction request with a result.
     *
     * This method validates that the result corresponds to the current pending request.
     * If validation passes, the result is published and the request is cleared.
     *
     * @param result The interaction result to be published.
     */
    suspend fun resolve(result: InteractionResult)
}

/**
 * Rejects the current pending interaction request.
 *
 * This is a convenience extension function that creates an [InteractionRejected] result
 * for the current pending request and resolves it through the [InteractionManager].
 *
 * If no request is pending, a warning message is logged and the function returns without action.
 *
 * @param reason Optional reason for the rejection (e.g., "user_cancelled", "timeout").
 */
suspend fun InteractionManager.reject(reason: String? = null) {
    val currentRequest = request.value

    if (currentRequest == null) {
        println("[InteractionManager]: reject() called without pending request")
        return
    }

    resolve(InteractionRejected(currentRequest, reason))
}

/**
 * Resolves the current pending interaction request with a value.
 *
 * This is a convenience extension function that creates an [InteractionResolved] result
 * for the current pending request with the provided value, and resolves it through the [InteractionManager].
 *
 * If no request is pending, a warning message is logged and the function returns without action.
 *
 * @param value The value to resolve the interaction with, or null if no value is provided.
 */
suspend fun <T> InteractionManager.resolve(value: T?) {
    val currentRequest = request.value

    if (currentRequest == null) {
        println("[InteractionManager]: resolve() called without pending request")
        return
    }

    resolve(InteractionResolved(currentRequest, value))
}

/**
 * Default implementation of [InteractionManager] with thread-safe request/result handling.
 *
 * This implementation uses a [Mutex] to ensure thread-safe access to the internal state flows,
 * preventing race conditions when multiple coroutines attempt to request or resolve interactions
 * concurrently.
 *
 * Features:
 * - Thread-safe request and result management
 * - Automatic validation that results match pending requests
 * - Clears previous results when a new request is made
 * - Emits null values when requests/results are cleared
 */
internal class DefaultInteractionManager : InteractionManager {
    private val mutex = Mutex()

    private val mRequest = MutableStateFlow<InteractionRequest?>(null)

    private val mResult = MutableStateFlow<InteractionResult?>(null)

    override val request: StateFlow<InteractionRequest?> = mRequest.asStateFlow()
    override val result: StateFlow<InteractionResult?> = mResult.asStateFlow()


    override suspend fun request(request: InteractionRequest) {
        mutex.withLock {
            mResult.value = null
            mRequest.value = request
        }
    }

    override suspend fun resolve(result: InteractionResult) {
        mutex.withLock {
            if (mRequest.value?.let { it == result.request } == true) {
                mResult.value = result
                mRequest.value = null
            } else {
                println("[DefaultInteractionManager]: resolve() called with a request different from the pending one.")
            }
        }
    }
}

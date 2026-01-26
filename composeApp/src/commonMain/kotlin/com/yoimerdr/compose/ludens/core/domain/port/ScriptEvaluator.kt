package com.yoimerdr.compose.ludens.core.domain.port

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Port interface for evaluating scripts.
 *
 * This interface defines the contract for executing scripts in the application,
 * allowing platform-specific implementations to handle script evaluation.
 */
interface ScriptEvaluator  {
    /**
     * Evaluates a script and optionally invokes a callback with the result.
     *
     * @param script The script code to evaluate.
     * @param callback Optional callback to receive the evaluation result as a string.
     */
    fun evaluateScript(
        script: String,
        callback: ((String) -> Unit)? = null,
    )
}


suspend fun ScriptEvaluator.evaluatingScript(script: String): String {
    return suspendCoroutine { continuation ->
        evaluateScript(script) {
            continuation.resume(it)
        }
    }
}
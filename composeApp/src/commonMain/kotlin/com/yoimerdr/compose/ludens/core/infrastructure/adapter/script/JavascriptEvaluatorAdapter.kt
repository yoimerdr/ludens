package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script

import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator

typealias JavascriptEvaluate = (String, ((String) -> Unit)?) -> Unit

/**
 * Adapter implementation of [ScriptEvaluator].
 *
 * Delegates script evaluation to a provided evaluator function, typically
 * connected to a web view or JavaScript engine.
 *
 * @property evaluator Function that evaluates JavaScript code and optionally
 * returns a result via callback.
 */
class JavascriptEvaluatorAdapter(
    private val evaluator: JavascriptEvaluate,
) : ScriptEvaluator {
    /**
     * Evaluates a JavaScript script.
     *
     * @param script The JavaScript code to evaluate.
     * @param callback Optional callback to receive the evaluation result as a string.
     */
    override fun evaluateScript(
        script: String,
        callback: ((String) -> Unit)?,
    ) {
        evaluator(script, callback)
    }

}
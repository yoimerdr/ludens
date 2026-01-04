package com.yoimerdr.compose.ludens.core.infrastructure.adapter.script

import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEvent
import com.yoimerdr.compose.ludens.core.domain.model.key.KeyEventType
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventEvaluator
import com.yoimerdr.compose.ludens.core.domain.port.KeyEventScriptBuilder
import com.yoimerdr.compose.ludens.core.domain.port.KeyScriptDeclaration
import com.yoimerdr.compose.ludens.core.domain.port.ScriptEvaluator
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.JavascriptKeyEventScriptBuilder

/**
 * Adapter implementation of [KeyEventEvaluator].
 *
 * This adapter bridges the domain's key event evaluation requirements with a script evaluator,
 * building and executing key event scripts using a configurable script builder.
 *
 * @property evaluator The [ScriptEvaluator] used to execute the generated key event scripts.
 * @property timeout The default timeout in milliseconds for automatic key release events. Default is 150ms.
 * @property builder The [KeyEventScriptBuilder] used to construct key event scripts.
 * Defaults to [JavascriptKeyEventScriptBuilder].
 */
class KeyEventEvaluatorAdapter(
    private val evaluator: ScriptEvaluator,
    private val timeout: Int = 150,
    private val builder: KeyEventScriptBuilder = JavascriptKeyEventScriptBuilder(),
) : KeyEventEvaluator {

    init {
        builder.restart()
    }

    override fun evaluateKeyEventScript(callback: ((String) -> Unit)?) {
        evaluator.evaluateScript(
            builder.build(),
            callback
        )
    }

    override fun prepareKeyEvent(key: KeyEvent) {
        this.builder.add(key)
    }

    override fun prepareKeyEvent(
        type: KeyEventType,
        timeout: Boolean,
        builder: KeyScriptDeclaration,
    ) {
        this.builder
            .add(builder(type, null))

        if (timeout) {
            this.builder
                .add(
                    builder(
                        when (type) {
                            KeyEventType.Down -> KeyEventType.Up
                            KeyEventType.Up -> KeyEventType.Down
                        },
                        this.timeout
                    )
                )
        }
    }

    override fun prepareKeyEvent(
        type: KeyEventType,
        builder: KeyScriptDeclaration,
    ) = prepareKeyEvent(type, false, builder)

    override fun evaluateKeyEvent(
        type: KeyEventType,
        timeout: Boolean,
        builder: KeyScriptDeclaration,
    ) {
        prepareKeyEvent(type, timeout, builder)
        evaluateKeyEventScript()
    }

    override fun evaluateKeyEvent(
        type: KeyEventType,
        builder: KeyScriptDeclaration,
    ) = evaluateKeyEvent(type, false, builder)

    override fun evaluateKeyEvent(key: KeyEvent) {
        this.builder.add(key)
        evaluateKeyEventScript()
    }
}
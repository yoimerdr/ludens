package com.yoimerdr.compose.ludens.ui.components.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.yoimerdr.compose.ludens.ui.components.interaction.DefaultInteractionManager
import com.yoimerdr.compose.ludens.ui.components.interaction.InteractionManager
import com.yoimerdr.compose.ludens.ui.components.interaction.InteractionRejected
import com.yoimerdr.compose.ludens.ui.components.interaction.InteractionResolved

/**
 * Composition local for providing an [InteractionManager] instance throughout the composition tree.
 *
 * This allows any composable in the hierarchy to access the [InteractionManager] without
 * explicit parameter passing.
 *
 * @throws IllegalStateException if accessed before an [InteractionManager] is provided via [ProvideInteractionManager].
 */
val LocalInteractionManager =
    staticCompositionLocalOf<InteractionManager> { error("No InteractionManager provided") }


/**
 * Collects and handles interaction results from an [InteractionManager].
 *
 * This composable function observes the [InteractionManager.result] flow and invokes
 * the appropriate callback when a result is emitted.
 *
 * @param manager The [InteractionManager] to observe. Defaults to [LocalInteractionManager].
 * @param onReject Optional callback invoked when an interaction is rejected.
 * @param onResolve Callback invoked when an interaction is successfully resolved.
 */
@Composable
fun CollectInteractionResult(
    manager: InteractionManager = LocalInteractionManager.current,
    onReject: (suspend (InteractionRejected) -> Unit)? = null,
    onResolve: suspend (InteractionResolved<*>) -> Unit,
) {
    LaunchedEffect(manager) {
        manager.result.collect {
            when (it) {
                is InteractionRejected -> {
                    onReject?.invoke(it)
                }

                is InteractionResolved<*> -> {
                    onResolve(it)
                }
            }
        }
    }
}

/**
 * Provides an [InteractionManager] instance to the composition tree.
 *
 * This composable makes the [InteractionManager] available to all child composables
 * through [LocalInteractionManager].
 *
 * @param manager The [InteractionManager] to provide.
 * @param content The composable content that will have access to the [InteractionManager].
 */
@Composable
@NonSkippableComposable
fun ProvideInteractionManager(
    manager: InteractionManager,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalInteractionManager provides manager,
        content = content,
    )
}

/**
 * Provides an [InteractionManager] instance to the composition tree.
 *
 * This composable makes the [InteractionManager] available to all child composables
 * through [LocalInteractionManager].
 *
 * @param content The composable content that will have access to the [InteractionManager].
 */
@Composable
@NonSkippableComposable
fun ProvideInteractionManager(
    content: @Composable () -> Unit,
) {
    ProvideInteractionManager(
        manager = remember { DefaultInteractionManager() },
        content = content,
    )
}
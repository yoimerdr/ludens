package com.yoimerdr.compose.ludens.ui.state.drag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.yoimerdr.compose.ludens.ui.state.Edge


/**
 * The state of a draggable component.
 */
interface ClampedDraggableState {
    /** The bounds within which the component can be dragged. */
    var bounds: Rect

    /** The current offset of the draggable component. */
    val offset: Offset

    /** Callback invoked when a drag gesture starts. */
    val onStart: ((Offset) -> Unit)

    /** Callback invoked when the offset changes. */
    val onChange: ((Offset) -> Unit)

    /** Callback invoked when a drag gesture ends. */
    val onEnd: ((Offset) -> Unit)

    /**
     * Updates the offset by the given delta.
     *
     * @param delta The offset delta to apply.
     */
    fun dragBy(delta: Offset)

    /**
     * Updates the offset value.
     *
     * @param offset The new offset value to apply.
     * */
    fun drag(offset: Offset)

    /**
     * Resets the offset to its initial value.
     */
    fun reset()
}


/**
 * Default implementation of [ClampedDraggableState].
 *
 * @param initialOffset The initial offset.
 * @param initialBounds The initial bounds.
 * @param onStart Callback invoked when dragging starts.
 * @param onChange Callback invoked when the offset changes.
 * @param onEnd Callback invoked when dragging ends.
 */
@Stable
class DefaultClampedDraggableState(
    val initialOffset: Offset = Offset.Zero,
    initialBounds: Rect = Rect.Zero,
    override val onStart: (Offset) -> Unit,
    override val onChange: (Offset) -> Unit,
    override val onEnd: (Offset) -> Unit,
) : ClampedDraggableState {
    private val _offset = mutableStateOf(initialOffset)

    override var bounds: Rect by mutableStateOf(initialBounds)

    override var offset: Offset
        get() = _offset.value
        private set(value) {
            val x = value.x.coerceIn(bounds.left, bounds.right.coerceAtLeast(bounds.left))
            val y = value.y.coerceIn(bounds.top, bounds.bottom.coerceAtLeast(bounds.top))
            _offset.value = Offset(
                x,
                y
            )
            onChange(_offset.value)
        }

    override fun dragBy(delta: Offset) {
        drag(offset + delta)
    }

    override fun drag(offset: Offset) {
        this.offset = offset
    }

    override fun reset() {
        offset = initialOffset
    }
}

/**
 * Drags the component to the given edge.
 *
 * @param edge The edge to drag to.
 * */
fun ClampedDraggableState.drag(edge: Edge) {
    val bounds = bounds
    val offset = offset
    val target = when (edge) {
        Edge.Top -> offset.copy(y = bounds.top)
        Edge.Bottom -> offset.copy(y = bounds.bottom)
        Edge.Left -> offset.copy(x = bounds.left)
        else -> offset.copy(x = bounds.right)
    }

    drag(target)
}

/**
 * Creates and remembers a [ClampedDraggableState].
 *
 * @param initialOffset The initial offset.
 * @param initialBounds The initial bounds.
 * @param onChange Callback invoked when the offset changes.
 * @param onEnd Callback invoked when dragging ends.
 * @param onStart Callback invoked when dragging starts.
 */
@Composable
fun rememberClampedDraggableState(
    initialOffset: Offset = Offset.Zero,
    initialBounds: Rect = Rect.Zero,
    onChange: ((Offset) -> Unit)? = null,
    onEnd: ((Offset) -> Unit)? = null,
    onStart: ((Offset) -> Unit)? = null,
): ClampedDraggableState {
    val onChangeState = rememberUpdatedState(onChange)
    val onDragEndState = rememberUpdatedState(onEnd)
    val onStartState = rememberUpdatedState(onStart)

    return remember(initialOffset, initialBounds) {
        DefaultClampedDraggableState(
            initialOffset = initialOffset,
            initialBounds = initialBounds,
            onStart = {
                onStartState.value?.invoke(it)
            },
            onChange = {
                onChangeState.value?.invoke(it)
            },
            onEnd = {
                onDragEndState.value?.invoke(it)
            }
        )
    }
}
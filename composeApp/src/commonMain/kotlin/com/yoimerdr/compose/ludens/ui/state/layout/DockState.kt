package com.yoimerdr.compose.ludens.ui.state.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import com.yoimerdr.compose.ludens.ui.state.Edge
import kotlin.math.min

/**
 * The docking mode of a element.
 */
enum class DockMode {
    /** The element is idle, neither docked nor static. */
    Idle,

    /** The element is docked to an edge and can be hidden. */
    Docked,

    /** The element is static and remains visible. */
    Static
}

/**
 * The strategy for determining the dock edge.
 */
enum class DockStrategy {
    /** Dock to the nearest horizontal edge (left or right). */
    Horizontal,

    /** Dock to the nearest vertical edge (top or bottom). */
    Vertical,

    /** Automatically select the nearest edge in any direction. */
    Auto
}

/**
 * The state of a dockable element.
 */
interface DockState {
    /** The edge to which the element is (or will be) docked. */
    val edge: Edge

    /** The current docking mode. */
    val mode: DockMode

    /** The offset to apply when the element is (or not) docked. */
    val offset: IntOffset

    /** The bounds of the element relative to its container. */
    val bounds: Rect

    /**
     * Sets the bounds of the element when it is open.
     *
     * @param bounds The bounds relative to the container.
     */
    fun setOpenBounds(bounds: Rect)

    /**
     * Sets the edge to which the element should dock.
     *
     * @param edge The dock edge.
     */
    fun setDockEdge(edge: Edge)

    /**
     * Puts the element in standby ([DockMode.Idle] state).
     */
    fun standby()

    /**
     * Docks the element to the [edge].
     */
    fun dock()

    /**
     * Makes the element static (always visible).
     */
    fun static()
}

/**
 * Default implementation of [DockState].
 *
 * @param initialValue The initial docking mode.
 * @param initialEdge The initial dock edge.
 */
@Stable
class DefaultDockState(
    initialValue: DockMode = DockMode.Docked,
    initialEdge: Edge = Edge.Left,
) : DockState {

    override var edge by mutableStateOf(initialEdge)
        private set

    override var mode by mutableStateOf(initialValue)
        private set

    override var bounds: Rect by mutableStateOf(Rect.Zero)
        private set

    override val offset: IntOffset
        get() {
            if (!isDocked)
                return IntOffset.Zero

            val (left, top, right, bottom) = bounds

            return when (edge) {
                Edge.Left -> Offset(-left, 0f)
                Edge.Right -> Offset(right, 0f)
                Edge.Top -> Offset(0f, -top)
                Edge.Bottom -> Offset(0f, bottom)
            }.round()
        }


    override fun standby() {
        mode = DockMode.Idle
    }

    override fun static() {
        mode = DockMode.Static
    }

    override fun dock() {
        mode = DockMode.Docked
    }

    override fun setOpenBounds(bounds: Rect) {
        if (bounds != this.bounds) {
            this.bounds = bounds
        }
    }

    override fun setDockEdge(edge: Edge) {
        if (edge != this.edge) {
            this.edge = edge
        }
    }
}


/**
 * Whether the element is docked.
 */
val DockState.isDocked: Boolean
    get() = mode == DockMode.Docked

/**
 * Whether the element is static.
 */
val DockState.isStatic: Boolean
    get() = mode == DockMode.Static

/**
 * Sets the bounds of the element when it is open and determines the dock edge.
 *
 * @param container The bounds of the container.
 * @param bounds The bounds of the element.
 * @param strategy The strategy for determining the dock edge.
 */
fun DockState.setOpenBounds(
    container: Rect,
    bounds: Rect,
    strategy: DockStrategy = DockStrategy.Auto,
) {
    val (left, top) = bounds.topLeft
    val right = container.width - (left + bounds.width)
    val bottom = container.height - (top + bounds.height)

    val edge = when (strategy) {
        DockStrategy.Horizontal -> if (left < right) Edge.Left else Edge.Right
        DockStrategy.Vertical -> if (top < bottom) Edge.Top else Edge.Bottom
        DockStrategy.Auto -> {
            val horizontal = min(left, right)
            val vertical = min(top, bottom)
            if (horizontal < vertical) {
                if (left < right) Edge.Left else Edge.Right
            } else {
                if (top < bottom) Edge.Top else Edge.Bottom
            }
        }
    }

    setOpenBounds(
        Rect(
            left = left,
            top = top,
            right = right,
            bottom = bottom
        )
    )

    setDockEdge(edge)
}


/**
 * Creates and remembers a [DockState].
 *
 * @param initialState The initial docking mode.
 * @param initialEdge The initial dock edge.
 */
@Composable
fun rememberDockState(
    initialState: DockMode = DockMode.Docked,
    initialEdge: Edge = Edge.Left,
): DockState {
    return remember(initialEdge, initialState) {
        DefaultDockState(
            initialValue = initialState,
            initialEdge = initialEdge
        )
    }
}
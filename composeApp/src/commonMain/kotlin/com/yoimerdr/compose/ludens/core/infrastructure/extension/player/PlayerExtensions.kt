package com.yoimerdr.compose.ludens.core.infrastructure.extension.player

import com.yoimerdr.compose.ludens.core.domain.port.player.GraphicPlayer
import com.yoimerdr.compose.ludens.core.domain.port.player.InputPlayer
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.GraphicsKey
import com.yoimerdr.compose.ludens.core.infrastructure.adapter.script.key.InputKey
import com.yoimerdr.compose.ludens.core.infrastructure.extension.key.toEvent

/**
 * Triggers the F2 key event on this graphic player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun GraphicPlayer.f2(pressed: Boolean = false) = onKeyEvent(GraphicsKey.F2.toEvent(), pressed)

/**
 * Triggers the F3 key event on this graphic player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun GraphicPlayer.f3(pressed: Boolean = false) = onKeyEvent(GraphicsKey.F3.toEvent(), pressed)

/**
 * Triggers the F4 key event on this graphic player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun GraphicPlayer.f4(pressed: Boolean = false) = onKeyEvent(GraphicsKey.F4.toEvent(), pressed)

/**
 * Triggers the Enter key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.enter(pressed: Boolean = false) = onKeyEvent(InputKey.Enter.toEvent(), pressed)

/**
 * Triggers the Escape key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.escape(pressed: Boolean = false) = onKeyEvent(InputKey.Escape.toEvent(), pressed)

/**
 * Triggers the Shift key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.shift(pressed: Boolean = false) = onKeyEvent(InputKey.Shift.toEvent(), pressed)

/**
 * Triggers the Alt key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.alt(pressed: Boolean = false) = onKeyEvent(InputKey.Alt.toEvent(), pressed)

/**
 * Triggers the Control key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.control(pressed: Boolean = false) =
    onKeyEvent(InputKey.Control.toEvent(), pressed)

/**
 * Triggers the Up arrow key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.up(pressed: Boolean = false) = onKeyEvent(InputKey.Up.toEvent(), pressed)

/**
 * Triggers the Down arrow key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.down(pressed: Boolean = false) = onKeyEvent(InputKey.Down.toEvent(), pressed)

/**
 * Triggers the Left arrow key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.left(pressed: Boolean = false) = onKeyEvent(InputKey.Left.toEvent(), pressed)

/**
 * Triggers the Right arrow key event on this input player.
 *
 * @param pressed Whether the key is pressed (true) or released (false).
 */
fun InputPlayer.right(pressed: Boolean = false) = onKeyEvent(InputKey.Right.toEvent(), pressed)
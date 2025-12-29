package com.yoimerdr.compose.ludens.ui.extensions.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Applies a standard content padding to the modifier.
 *
 * @param start The padding to apply to the start.
 * @param top The padding to apply to the top.
 * @param end The padding to apply to the end.
 * @param bottom The padding to apply to the bottom.
 * @return A new modifier with the padding applied.
 */
fun Modifier.contentPadding(
    start: Dp = 24.dp, top: Dp = 24.dp, end: Dp = 24.dp, bottom: Dp = 24.dp,
) = this.padding(
    start = start,
    top = top,
    end = end,
    bottom = bottom,
)

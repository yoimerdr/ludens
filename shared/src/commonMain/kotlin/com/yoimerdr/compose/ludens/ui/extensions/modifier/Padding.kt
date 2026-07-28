package com.yoimerdr.compose.ludens.ui.extensions.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.takeOrElse
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing

/**
 * Applies a standard content padding to the modifier.
 *
 * @param start The padding to apply to the start.
 * @param top The padding to apply to the top.
 * @param end The padding to apply to the end.
 * @param bottom The padding to apply to the bottom.
 * @return A new modifier with the padding applied.
 */
@Composable
fun Modifier.contentPadding(
    start: Dp = Dp.Unspecified,
    top: Dp = Dp.Unspecified,
    end: Dp = Dp.Unspecified,
    bottom: Dp = Dp.Unspecified,
): Modifier {
    val spacing = LocalSpacing.current
    return this.padding(
        start = start.takeOrElse { spacing.medium },
        top = top.takeOrElse { spacing.medium },
        end = end.takeOrElse { spacing.medium },
        bottom = bottom.takeOrElse { spacing.medium },
    )
}

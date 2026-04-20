package com.yoimerdr.compose.ludens.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing

/**
 * A composable that arranges two components with space between them.
 *
 * @param modifier The modifier to be applied to the component.
 * @param prefix The composable to be placed at the start.
 * @param suffix The composable to be placed at the end.
 */
@Composable
fun Spaced(
    modifier: Modifier = Modifier,
    prefix: @Composable RowScope.() -> Unit,
    suffix: @Composable RowScope.() -> Unit,
) {
    val spacing = LocalSpacing.current
    FlowRow(
        modifier = modifier,
        itemVerticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        prefix()
        suffix()
    }
}
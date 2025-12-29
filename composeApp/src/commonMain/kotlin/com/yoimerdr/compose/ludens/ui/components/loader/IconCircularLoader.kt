package com.yoimerdr.compose.ludens.ui.components.loader

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons
import com.yoimerdr.compose.ludens.ui.icons.filled.Spinner
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

/**
 * A circular loader that displays a spinner icon.
 *
 * @param modifier The modifier to be applied to the loader.
 * @param contentDescription The content description for the spinner icon.
 * @param label A label for the transition, used for debugging.
 * @param animationDuration The duration of one rotation animation cycle in milliseconds.
 * @param animationEasing The easing function to be used for the rotation animation.
 * @param color The color of the spinner icon.
 */
@Composable
fun SpinnerLoader(
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(Res.string.loading),
    label: String = "IconLoader",
    animationDuration: Int = 2000,
    animationEasing: Easing = LinearEasing,
    color: Color = LocalContentColor.current,
) {
    IconCircularLoader(
        imageVector = LudensIcons.Filled.Spinner,
        modifier = modifier.size(48.dp),
        contentDescription = contentDescription,
        label = label,
        animationDuration = animationDuration,
        animationEasing = animationEasing,
        color = color
    )
}

/**
 * A circular loader that displays a rotating icon.
 *
 * @param imageVector The vector graphic to be displayed as the icon.
 * @param modifier The modifier to be applied to the loader.
 * @param contentDescription The content description for the icon.
 * @param label A label for the transition, used for debugging.
 * @param animationDuration The duration of one rotation animation cycle in milliseconds.
 * @param animationEasing The easing function to be used for the rotation animation.
 * @param color The color of the icon.
 */
@Composable
fun IconCircularLoader(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(Res.string.loading),
    label: String = "IconLoader",
    animationDuration: Int = 1000,
    animationEasing: Easing = LinearEasing,
    color: Color = LocalContentColor.current,
) {

    CircularLoader(
        label = label,
        animationDuration = animationDuration,
        animationEasing = animationEasing,
    ) {
        Icon(
            modifier = it
                .then(modifier),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = color
        )
    }
}
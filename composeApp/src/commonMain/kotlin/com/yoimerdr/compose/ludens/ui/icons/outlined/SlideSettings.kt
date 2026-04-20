package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Slide Settings icon.
 *
 * This icon is a transformation of the outlined `Slide Settings` icon from Fluent Icons.
 */
val LudensIcons.Outlined.SlideSettings: ImageVector
    get() {
        if (_SlideSettings != null) {
            return _SlideSettings!!
        }
        _SlideSettings = ImageVector.Builder(
            name = "SlideSettings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(4.75f, 4f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 6.75f)
                verticalLineToRelative(10.5f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.75f, 20f)
                horizontalLineToRelative(6.748f)
                arcToRelative(
                    6.452f,
                    6.452f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.422f,
                    -1.5f
                )
                lineTo(4.75f, 18.5f)
                curveToRelative(-0.69f, 0f, -1.25f, -0.56f, -1.25f, -1.25f)
                lineTo(3.5f, 6.75f)
                curveToRelative(0f, -0.69f, 0.56f, -1.25f, 1.25f, -1.25f)
                horizontalLineToRelative(14.5f)
                curveToRelative(0.69f, 0f, 1.25f, 0.56f, 1.25f, 1.25f)
                verticalLineToRelative(4.982f)
                arcTo(6.518f, 6.518f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 12.81f)
                lineTo(22f, 6.75f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 19.25f, 4f)
                lineTo(4.75f, 4f)
                close()
                moveTo(14.278f, 13.975f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.441f,
                    2.497f
                )
                lineToRelative(-0.584f, 0.144f)
                arcToRelative(
                    5.729f,
                    5.729f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.006f,
                    1.807f
                )
                lineToRelative(0.54f, 0.13f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.45f,
                    2.51f
                )
                lineToRelative(-0.187f, 0.632f)
                curveToRelative(0.44f, 0.386f, 0.94f, 0.699f, 1.484f, 0.921f)
                lineToRelative(0.494f, -0.518f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.899f, 0f)
                lineToRelative(0.498f, 0.525f)
                arcToRelative(
                    5.281f,
                    5.281f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    1.483f,
                    -0.913f
                )
                lineToRelative(-0.198f, -0.686f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.441f,
                    -2.496f
                )
                lineToRelative(0.584f, -0.144f)
                arcToRelative(
                    5.716f,
                    5.716f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.006f,
                    -1.808f
                )
                lineToRelative(-0.54f, -0.13f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.45f,
                    -2.51f
                )
                lineToRelative(0.187f, -0.63f)
                arcToRelative(
                    5.278f,
                    5.278f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.484f,
                    -0.923f
                )
                lineToRelative(-0.493f, 0.519f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.9f, 0f)
                lineToRelative(-0.498f, -0.525f)
                curveToRelative(-0.544f, 0.22f, -1.044f, 0.53f, -1.483f, 0.912f)
                lineToRelative(0.198f, 0.686f)
                close()
                moveTo(17.5f, 19f)
                curveToRelative(-0.8f, 0f, -1.45f, -0.672f, -1.45f, -1.5f)
                curveToRelative(0f, -0.829f, 0.65f, -1.5f, 1.45f, -1.5f)
                curveToRelative(0.8f, 0f, 1.45f, 0.671f, 1.45f, 1.5f)
                curveToRelative(0f, 0.828f, -0.65f, 1.5f, -1.45f, 1.5f)
                close()
            }
        }.build()

        return _SlideSettings!!
    }

@Suppress("ObjectPropertyName")
private var _SlideSettings: ImageVector? = null

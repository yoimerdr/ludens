package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Top Speed icon.
 *
 * This icon is a transformation of the filled `Top Speed` icon from Fluent Icons.
 */
val LudensIcons.Filled.TopSpeed: ImageVector
    get() {
        if (_TopSpeed != null) {
            return _TopSpeed!!
        }
        _TopSpeed = ImageVector.Builder(
            name = "Filled.TopSpeed",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(10.05f, 15.42f)
                lineToRelative(6.256f, -8.475f)
                arcToRelative(
                    0.694f,
                    0.694f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.235f,
                    0.57f
                )
                lineToRelative(-0.03f, 0.098f)
                lineToRelative(-3.87f, 9.799f)
                arcToRelative(
                    2.07f,
                    2.07f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    -3.737f,
                    -1.765f
                )
                lineToRelative(0.069f, -0.116f)
                lineToRelative(0.076f, -0.11f)
                lineToRelative(6.257f, -8.476f)
                lineToRelative(-6.257f, 8.476f)
                close()
                moveTo(18.61f, 7.414f)
                arcToRelative(
                    10.66f,
                    10.66f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    2.022f,
                    2.172f
                )
                curveToRelative(0.524f, 0.749f, 1.03f, 1.656f, 1.32f, 2.398f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    -1.397f,
                    0.547f
                )
                arcToRelative(
                    8.238f,
                    8.238f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.378f,
                    -0.812f
                )
                lineToRelative(-2.05f, 1.183f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.834f,
                    -1.242f
                )
                lineToRelative(0.085f, -0.057f)
                lineToRelative(2.018f, -1.166f)
                lineToRelative(-0.23f, -0.314f)
                arcToRelative(
                    9.156f,
                    9.156f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.058f,
                    -1.16f
                )
                lineToRelative(0.38f, -0.964f)
                curveToRelative(0.038f, -0.096f, 0.067f, -0.194f, 0.087f, -0.292f)
                lineToRelative(0.024f, -0.147f)
                lineToRelative(0.01f, -0.146f)
                close()
                moveTo(15.98f, 5.853f)
                arcToRelative(
                    1.715f,
                    1.715f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.406f,
                    0.328f
                )
                lineToRelative(-0.114f, 0.14f)
                lineToRelative(-0.54f, 0.733f)
                arcToRelative(
                    9.205f,
                    9.205f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -2.17f,
                    -0.47f
                )
                verticalLineToRelative(2.672f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.493f,
                    0.102f
                )
                lineToRelative(-0.007f, -0.102f)
                verticalLineToRelative(-2.69f)
                arcTo(
                    9.108f,
                    9.108f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    6.658f,
                    8.2f
                )
                curveToRelative(-0.816f, 0.572f, -1.528f, 1.322f, -2.119f, 2.205f)
                lineToRelative(2.082f, 1.202f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.658f,
                    1.344f
                )
                lineToRelative(-0.092f, -0.045f)
                lineToRelative(-2.074f, -1.197f)
                curveToRelative(-0.128f, 0.266f, -0.246f, 0.54f, -0.356f, 0.821f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.398f,
                    -0.543f
                )
                curveToRelative(0.807f, -2.075f, 2.08f, -3.843f, 3.754f, -5.016f)
                arcToRelative(
                    10.642f,
                    10.642f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    10.183f,
                    -1.117f
                )
                close()
            }
        }.build()

        return _TopSpeed!!
    }

@Suppress("ObjectPropertyName")
private var _TopSpeed: ImageVector? = null

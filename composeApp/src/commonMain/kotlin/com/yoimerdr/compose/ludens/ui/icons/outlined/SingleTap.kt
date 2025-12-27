package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Single Tap icon.
 *
 * This icon is a transformation of the outlined `Tap Single` icon from Fluent Icons.
 */
val LudensIcons.Outlined.SingleTap: ImageVector
    get() {
        if (_SingleTap != null) {
            return _SingleTap!!
        }
        _SingleTap =
            ImageVector.Builder(
                name = "Outlined.SingleTap",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    moveTo(11.75f, 6f)
                    curveToRelative(1.349f, 0f, 2.179f, 0.969f, 2.246f, 2.329f)
                    lineToRelative(0.004f, 0.18f)
                    verticalLineToRelative(2.115f)
                    lineToRelative(2.22f, 0.403f)
                    arcToRelative(
                        3.252f,
                        3.252f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        2.601f,
                        3.857f
                    )
                    lineToRelative(-0.043f, 0.18f)
                    lineToRelative(-1.048f, 3.922f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -1.689f,
                        1.617f
                    )
                    lineToRelative(-0.165f, 0.03f)
                    lineToRelative(-2.418f, 0.347f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -2.28f,
                        -1.124f
                    )
                    lineToRelative(-0.08f, -0.154f)
                    lineToRelative(-0.029f, -0.063f)
                    arcToRelative(
                        3.465f,
                        3.465f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -1.026f,
                        -1.283f
                    )
                    lineToRelative(-0.194f, -0.139f)
                    lineToRelative(-1.883f, -1.255f)
                    lineToRelative(-0.094f, -0.059f)
                    lineToRelative(-0.098f, -0.052f)
                    lineToRelative(-2.362f, -1.176f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -0.416f,
                        -0.655f
                    )
                    curveToRelative(-0.025f, -1.108f, 0.465f, -1.963f, 1.419f, -2.44f)
                    curveToRelative(0.702f, -0.351f, 1.635f, -0.331f, 2.826f, 0.017f)
                    lineToRelative(0.26f, 0.079f)
                    lineTo(9.501f, 8.508f)
                    curveTo(9.5f, 7.055f, 10.342f, 6f, 11.75f, 6f)
                    close()
                    moveTo(11.75f, 7.5f)
                    curveToRelative(-0.46f, 0f, -0.712f, 0.275f, -0.746f, 0.866f)
                    lineTo(11f, 8.508f)
                    verticalLineToRelative(5.244f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -1.036f,
                        0.694f
                    )
                    curveToRelative(-1.46f, -0.603f, -2.441f, -0.743f, -2.878f, -0.524f)
                    curveToRelative(-0.254f, 0.127f, -0.42f, 0.29f, -0.51f, 0.518f)
                    lineToRelative(-0.039f, 0.12f)
                    lineToRelative(1.906f, 0.948f)
                    lineToRelative(0.18f, 0.097f)
                    lineToRelative(0.175f, 0.108f)
                    lineToRelative(1.883f, 1.256f)
                    arcToRelative(
                        4.966f,
                        4.966f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        1.626f,
                        1.793f
                    )
                    lineToRelative(0.122f, 0.244f)
                    lineToRelative(0.03f, 0.063f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0.677f,
                        0.434f
                    )
                    lineToRelative(0.109f, -0.008f)
                    lineToRelative(2.418f, -0.347f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0.582f,
                        -0.444f
                    )
                    lineToRelative(0.036f, -0.105f)
                    lineToRelative(1.048f, -3.923f)
                    arcToRelative(
                        1.75f,
                        1.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -1.239f,
                        -2.142f
                    )
                    lineToRelative(-0.069f, -0.017f)
                    lineToRelative(-2.905f, -0.53f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -0.608f,
                        -0.63f
                    )
                    lineToRelative(-0.008f, -0.107f)
                    lineTo(12.5f, 8.508f)
                    curveToRelative(0f, -0.69f, -0.255f, -1.008f, -0.75f, -1.008f)
                    close()
                    moveTo(11.749f, 2.5f)
                    arcToRelative(
                        5.75f,
                        5.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        5.393f,
                        7.749f
                    )
                    lineToRelative(-0.144f, -0.053f)
                    arcToRelative(
                        5.604f,
                        5.604f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.607f,
                        -0.175f
                    )
                    arcToRelative(
                        4.986f,
                        4.986f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.727f,
                        -0.112f
                    )
                    arcTo(
                        4.25f,
                        4.25f,
                        0f,
                        isMoreThanHalf = true,
                        isPositiveArc = false,
                        8.5f,
                        10.99f
                    )
                    arcToRelative(
                        5.66f,
                        5.66f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.824f,
                        0.123f
                    )
                    curveToRelative(-0.351f, 0.085f, -0.588f, 0.17f, -0.753f, 0.263f)
                    arcTo(
                        5.75f,
                        5.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        11.75f,
                        2.5f
                    )
                    close()
                }
            }.build()

        return _SingleTap!!
    }

@Suppress("ObjectPropertyName")
private var _SingleTap: ImageVector? = null

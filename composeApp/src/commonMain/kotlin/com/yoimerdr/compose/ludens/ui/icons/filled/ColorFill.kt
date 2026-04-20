package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Color Fill icon.
 *
 * This icon is a transformation of the filled `Color Fill` icon from Fluent Icons.
 */
val LudensIcons.Filled.ColorFill: ImageVector
    get() {
        if (_ColorFill != null) {
            return _ColorFill!!
        }
        _ColorFill = ImageVector.Builder(
            name = "ColorFill",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(13.02f, 14f)
                curveToRelative(-0.013f, 0.122f, -0.02f, 0.248f, -0.02f, 0.375f)
                curveTo(13f, 16.23f, 14.394f, 18f, 16.5f, 18f)
                reflectiveCurveTo(20f, 16.23f, 20f, 14.375f)
                curveToRelative(0f, -0.111f, -0.006f, -0.22f, -0.016f, -0.328f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 16.5f)
                verticalLineToRelative(3f)
                arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.5f, 2.5f)
                horizontalLineToRelative(-15f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 19.5f)
                verticalLineToRelative(-3f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.5f, 14f)
                horizontalLineToRelative(2.086f)
                lineToRelative(0.907f, 0.907f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.242f, 0f)
                lineToRelative(0.907f, -0.907f)
                horizontalLineToRelative(0.378f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(14.342f, 12.994f)
                curveToRelative(-0.188f, 0.409f, -0.342f, 0.888f, -0.342f, 1.381f)
                curveTo(14f, 15.765f, 15.03f, 17f, 16.5f, 17f)
                reflectiveCurveToRelative(2.5f, -1.235f, 2.5f, -2.625f)
                curveToRelative(0f, -0.493f, -0.154f, -0.972f, -0.342f, -1.381f)
                arcToRelative(6.977f, 6.977f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.674f, -1.134f)
                arcToRelative(10.41f, 10.41f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.649f, -0.812f)
                lineToRelative(-0.305f, -0.328f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.06f, 0f)
                lineToRelative(-0.006f, 0.005f)
                curveToRelative(-0.058f, 0.062f, -0.214f, 0.226f, -0.3f, 0.323f)
                curveToRelative(-0.175f, 0.199f, -0.41f, 0.48f, -0.648f, 0.812f)
                arcToRelative(6.977f, 6.977f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.674f, 1.134f)
                close()
                moveTo(16.5f, 12.384f)
                curveToRelative(0.086f, 0.108f, 0.176f, 0.225f, 0.266f, 0.35f)
                curveToRelative(0.201f, 0.281f, 0.392f, 0.586f, 0.529f, 0.886f)
                curveToRelative(0.14f, 0.305f, 0.205f, 0.56f, 0.205f, 0.755f)
                curveToRelative(0f, 0.691f, -0.48f, 1.125f, -1f, 1.125f)
                curveToRelative(-0.519f, 0f, -1f, -0.434f, -1f, -1.125f)
                curveToRelative(0f, -0.194f, 0.065f, -0.45f, 0.205f, -0.755f)
                curveToRelative(0.138f, -0.3f, 0.328f, -0.605f, 0.53f, -0.886f)
                curveToRelative(0.09f, -0.125f, 0.18f, -0.242f, 0.265f, -0.35f)
                close()
                moveTo(9.75f, 1f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, 0.75f)
                verticalLineToRelative(1.756f)
                arcToRelative(2.033f, 2.033f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.093f, 0.087f)
                lineTo(4.664f, 7.836f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 2.828f)
                lineTo(8.2f, 14.2f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.828f, 0f)
                lineToRelative(4.243f, -4.243f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2.828f)
                lineToRelative(-3.536f, -3.536f)
                arcToRelative(1.992f, 1.992f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.235f, -0.578f)
                lineTo(10.5f, 1.75f)
                arcTo(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.75f, 1f)
                close()
                moveTo(9f, 5.621f)
                verticalLineToRelative(0.629f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.5f, 0f)
                lineTo(10.5f, 4.54f)
                arcToRelative(0.499f, 0.499f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.175f, 0.114f)
                lineToRelative(3.535f, 3.535f)
                curveToRelative(0.09f, 0.09f, 0.138f, 0.205f, 0.146f, 0.322f)
                lineTo(6.11f, 8.511f)
                lineTo(9f, 5.62f)
                close()
                moveTo(9.26f, 13.139f)
                lineTo(6.132f, 10.011f)
                horizontalLineToRelative(6.964f)
                lineToRelative(-3.128f, 3.128f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.708f, 0f)
                close()
            }
        }.build()

        return _ColorFill!!
    }

@Suppress("ObjectPropertyName")
private var _ColorFill: ImageVector? = null

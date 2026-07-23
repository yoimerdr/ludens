package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Color Fill icon.
 *
 * This icon is a transformation of the outlined `Color Fill` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ColorFill: ImageVector
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
                moveTo(9f, 1.75f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.5f, 0f)
                verticalLineToRelative(1.265f)
                curveToRelative(0.45f, 0.04f, 0.89f, 0.233f, 1.235f, 0.578f)
                lineToRelative(3.536f, 3.536f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 2.828f)
                lineTo(11.028f, 14.2f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.828f, 0f)
                lineToRelative(-3.536f, -3.536f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -2.828f)
                lineToRelative(4.243f, -4.243f)
                curveToRelative(0.03f, -0.03f, 0.061f, -0.06f, 0.093f, -0.087f)
                lineTo(9f, 1.75f)
                close()
                moveTo(9f, 6.25f)
                verticalLineToRelative(-0.629f)
                lineToRelative(-2.89f, 2.89f)
                horizontalLineToRelative(8.245f)
                arcToRelative(0.498f, 0.498f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.145f, -0.322f)
                lineToRelative(-3.535f, -3.535f)
                arcToRelative(0.498f, 0.498f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.175f, -0.114f)
                verticalLineToRelative(1.71f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, -1.5f, 0f)
                close()
                moveTo(6.132f, 10.01f)
                lineTo(9.26f, 13.14f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.707f, 0f)
                lineToRelative(3.129f, -3.13f)
                lineTo(6.132f, 10.01f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(6.586f, 14f)
                horizontalLineTo(4.5f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 16.5f)
                verticalLineToRelative(3f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.5f, 22f)
                horizontalLineToRelative(15f)
                arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.5f, -2.5f)
                verticalLineToRelative(-3f)
                arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.016f, -2.453f)
                arcToRelative(3.772f, 3.772f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.173f, 1.503f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.689f, 0.95f)
                verticalLineToRelative(3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 1f)
                horizontalLineToRelative(-15f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -1f)
                verticalLineToRelative(-3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                horizontalLineToRelative(3.835f)
                arcToRelative(2.995f, 2.995f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.842f, -0.593f)
                lineTo(6.586f, 14f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(14f, 14.375f)
                curveToRelative(0f, -0.493f, 0.154f, -0.972f, 0.342f, -1.381f)
                curveToRelative(0.19f, -0.416f, 0.438f, -0.806f, 0.674f, -1.134f)
                curveToRelative(0.237f, -0.331f, 0.473f, -0.613f, 0.649f, -0.812f)
                curveToRelative(0.085f, -0.097f, 0.241f, -0.261f, 0.3f, -0.323f)
                lineToRelative(0.005f, -0.005f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.06f, 0f)
                lineToRelative(0.305f, 0.328f)
                curveToRelative(0.176f, 0.199f, 0.412f, 0.48f, 0.65f, 0.812f)
                curveToRelative(0.235f, 0.328f, 0.483f, 0.718f, 0.673f, 1.134f)
                curveToRelative(0.188f, 0.409f, 0.342f, 0.888f, 0.342f, 1.381f)
                curveTo(19f, 15.765f, 17.971f, 17f, 16.5f, 17f)
                reflectiveCurveTo(14f, 15.765f, 14f, 14.375f)
                close()
                moveTo(16.766f, 12.735f)
                arcToRelative(8.416f, 8.416f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.266f, -0.35f)
                arcToRelative(8.416f, 8.416f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.266f, 0.35f)
                curveToRelative(-0.201f, 0.28f, -0.392f, 0.585f, -0.529f, 0.885f)
                curveToRelative(-0.14f, 0.305f, -0.205f, 0.56f, -0.205f, 0.755f)
                curveToRelative(0f, 0.691f, 0.48f, 1.125f, 1f, 1.125f)
                reflectiveCurveToRelative(1f, -0.434f, 1f, -1.125f)
                curveToRelative(0f, -0.194f, -0.065f, -0.45f, -0.205f, -0.755f)
                arcToRelative(5.493f, 5.493f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.53f, -0.886f)
                close()
            }
        }.build()

        return _ColorFill!!
    }

@Suppress("ObjectPropertyName")
private var _ColorFill: ImageVector? = null

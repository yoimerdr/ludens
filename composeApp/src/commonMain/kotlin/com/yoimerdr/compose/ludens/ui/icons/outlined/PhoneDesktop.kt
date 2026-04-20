package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Phone Desktop icon.
 *
 * This icon is a transformation of the outlined `Phone Desktop` icon from Fluent Icons.
 */
val LudensIcons.Outlined.PhoneDesktop: ImageVector
    get() {
        if (_PhoneDesktop != null) {
            return _PhoneDesktop!!
        }
        _PhoneDesktop = ImageVector.Builder(
            name = "PhoneDesktop",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(8.255f, 9f)
                curveToRelative(0.966f, 0f, 1.75f, 0.783f, 1.75f, 1.75f)
                verticalLineToRelative(9.5f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.255f, 22f)
                lineTo(3.75f, 22f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 20.25f)
                verticalLineToRelative(-9.5f)
                curveTo(2f, 9.783f, 2.784f, 9f, 3.75f, 9f)
                horizontalLineToRelative(4.505f)
                close()
                moveTo(8.255f, 10.5f)
                lineTo(3.75f, 10.5f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, 0.25f)
                verticalLineToRelative(9.5f)
                curveToRelative(0f, 0.138f, 0.112f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(4.505f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, -0.25f)
                verticalLineToRelative(-9.5f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -0.25f)
                close()
                moveTo(6.25f, 18f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.102f, 1.493f)
                lineToRelative(-0.102f, 0.007f)
                horizontalLineToRelative(-0.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.102f, -1.493f)
                lineTo(5.75f, 18f)
                horizontalLineToRelative(0.5f)
                close()
                moveTo(19.75f, 2f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.245f, 2.096f)
                lineTo(22f, 4.25f)
                verticalLineToRelative(9f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.096f, 2.245f)
                lineToRelative(-0.154f, 0.005f)
                horizontalLineToRelative(-3.746f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(1.246f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.102f, 1.493f)
                lineTo(17.25f, 19f)
                lineTo(11f, 19f)
                verticalLineToRelative(-1.5f)
                horizontalLineToRelative(3.504f)
                verticalLineToRelative(-2f)
                lineTo(11f, 15.5f)
                lineTo(11f, 14f)
                horizontalLineToRelative(8.75f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.743f, -0.648f)
                lineToRelative(0.007f, -0.102f)
                verticalLineToRelative(-9f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                lineTo(6.25f, 3.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.743f, 0.648f)
                lineTo(5.5f, 4.25f)
                lineTo(5.5f, 8f)
                lineTo(4f, 8f)
                lineTo(4f, 4.25f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.096f, -2.245f)
                lineTo(6.25f, 2f)
                horizontalLineToRelative(13.5f)
                close()
            }
        }.build()

        return _PhoneDesktop!!
    }

@Suppress("ObjectPropertyName")
private var _PhoneDesktop: ImageVector? = null

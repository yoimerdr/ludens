package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Screen Shoot icon.
 *
 * This icon is a transformation of the filled `Screenshot` icon from Fluent Icons.
 */
val LudensIcons.Filled.ScreenShoot: ImageVector
    get() {
        if (_ScreenShoot != null) {
            return _ScreenShoot!!
        }
        _ScreenShoot = ImageVector.Builder(
            name = "Filled.ScreenShoot",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(17.25f, 3f)
                arcTo(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21f, 6.75f)
                verticalLineToRelative(10.5f)
                arcTo(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 17.25f, 21f)
                lineTo(6.75f, 21f)
                arcTo(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 17.25f)
                lineTo(3f, 6.75f)
                arcTo(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.75f, 3f)
                horizontalLineToRelative(10.5f)
                close()
                moveTo(17.25f, 13f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.743f, 0.648f)
                lineToRelative(-0.007f, 0.102f)
                lineTo(16.5f, 16f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.41f, 0.492f)
                lineTo(16f, 16.5f)
                horizontalLineToRelative(-2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.102f, 1.493f)
                lineToRelative(0.102f, 0.007f)
                lineTo(16f, 18f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.994f, -1.85f)
                lineTo(18f, 16f)
                verticalLineToRelative(-2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                close()
                moveTo(6.75f, 13f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.743f, 0.648f)
                lineTo(6f, 13.75f)
                lineTo(6f, 16f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.85f, 1.994f)
                lineTo(8f, 18f)
                horizontalLineToRelative(2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.102f, -1.493f)
                lineToRelative(-0.102f, -0.007f)
                lineTo(8f, 16.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.492f, -0.41f)
                lineTo(7.5f, 16f)
                verticalLineToRelative(-2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                close()
                moveTo(10.25f, 6f)
                lineTo(8f, 6f)
                lineToRelative(-0.15f, 0.005f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.844f, 1.838f)
                lineTo(6f, 8f)
                verticalLineToRelative(2.25f)
                lineToRelative(0.007f, 0.102f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.486f, 0f)
                lineToRelative(0.007f, -0.102f)
                lineTo(7.5f, 8f)
                lineToRelative(0.008f, -0.09f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.402f, -0.402f)
                lineTo(8f, 7.5f)
                horizontalLineToRelative(2.25f)
                lineToRelative(0.102f, -0.007f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -1.486f)
                lineTo(10.25f, 6f)
                close()
                moveTo(16f, 6f)
                horizontalLineToRelative(-2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.102f, 1.493f)
                lineToRelative(0.102f, 0.007f)
                lineTo(16f, 7.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.492f, 0.41f)
                lineTo(16.5f, 8f)
                verticalLineToRelative(2.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.493f, 0.102f)
                lineTo(18f, 10.25f)
                lineTo(18f, 8f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.85f, -1.995f)
                lineTo(16f, 6f)
                close()
            }
        }.build()

        return _ScreenShoot!!
    }

@Suppress("ObjectPropertyName")
private var _ScreenShoot: ImageVector? = null

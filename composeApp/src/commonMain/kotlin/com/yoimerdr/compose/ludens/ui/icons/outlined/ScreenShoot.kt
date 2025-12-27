package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Screen Shoot icon.
 *
 * This icon is a transformation of the outlined `Screenshot` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ScreenShoot: ImageVector
    get() {
        if (_ScreenShoot != null) {
            return _ScreenShoot!!
        }
        _ScreenShoot =
            ImageVector.Builder(
                name = "Outlined.ScreenShoot",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(fill = SolidColor(Color.White)) {
                    moveTo(17.25f, 3f)
                    arcTo(
                        3.75f,
                        3.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        21f,
                        6.75f
                    )
                    verticalLineToRelative(10.5f)
                    arcTo(
                        3.75f,
                        3.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        17.25f,
                        21f
                    )
                    lineTo(6.75f, 21f)
                    arcTo(
                        3.75f,
                        3.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        3f,
                        17.25f
                    )
                    lineTo(3f, 6.75f)
                    arcTo(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.75f, 3f)
                    horizontalLineToRelative(10.5f)
                    close()
                    moveTo(17.25f, 4.5f)
                    lineTo(6.75f, 4.5f)
                    arcTo(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        4.5f,
                        6.75f
                    )
                    verticalLineToRelative(10.5f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        2.25f,
                        2.25f
                    )
                    horizontalLineToRelative(10.5f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        2.25f,
                        -2.25f
                    )
                    lineTo(19.5f, 6.75f)
                    arcToRelative(
                        2.25f,
                        2.25f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -2.25f,
                        -2.25f
                    )
                    close()
                    moveTo(17.25f, 13f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0.75f,
                        0.75f
                    )
                    lineTo(18f, 16f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2f, 2f)
                    horizontalLineToRelative(-2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        -1.5f
                    )
                    lineTo(16f, 16.5f)
                    arcToRelative(
                        0.5f,
                        0.5f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0.5f,
                        -0.5f
                    )
                    verticalLineToRelative(-2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0.75f,
                        -0.75f
                    )
                    close()
                    moveTo(6.75f, 13f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0.75f,
                        0.75f
                    )
                    lineTo(7.5f, 16f)
                    arcToRelative(
                        0.5f,
                        0.5f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        0.5f,
                        0.5f
                    )
                    horizontalLineToRelative(2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        1.5f
                    )
                    lineTo(8f, 18f)
                    arcToRelative(
                        2f,
                        2f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -2f,
                        -2f
                    )
                    verticalLineToRelative(-2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0.75f,
                        -0.75f
                    )
                    close()
                    moveTo(8f, 6f)
                    horizontalLineToRelative(2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0.102f,
                        1.493f
                    )
                    lineToRelative(-0.102f, 0.007f)
                    lineTo(8f, 7.5f)
                    arcToRelative(
                        0.5f,
                        0.5f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.492f,
                        0.41f
                    )
                    lineTo(7.5f, 8f)
                    verticalLineToRelative(2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -1.493f,
                        0.102f
                    )
                    lineTo(6f, 10.25f)
                    lineTo(6f, 8f)
                    arcToRelative(
                        2f,
                        2f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        1.85f,
                        -1.995f
                    )
                    lineTo(8f, 6f)
                    horizontalLineToRelative(2.25f)
                    lineTo(8f, 6f)
                    close()
                    moveTo(16f, 6f)
                    arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 2f)
                    verticalLineToRelative(2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -1.5f,
                        0f
                    )
                    lineTo(16.5f, 8f)
                    arcToRelative(
                        0.5f,
                        0.5f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = false,
                        -0.5f,
                        -0.5f
                    )
                    horizontalLineToRelative(-2.25f)
                    arcToRelative(
                        0.75f,
                        0.75f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        0f,
                        -1.5f
                    )
                    lineTo(16f, 6f)
                    close()
                }
            }.build()

        return _ScreenShoot!!
    }

@Suppress("ObjectPropertyName")
private var _ScreenShoot: ImageVector? = null

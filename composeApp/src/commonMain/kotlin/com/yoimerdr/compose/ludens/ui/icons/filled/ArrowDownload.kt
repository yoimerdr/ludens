package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Download icon.
 *
 * This icon is a transformation of the filled `Arrow Download` icon from Fluent Icons.
 */
val LudensIcons.Filled.ArrowDownload: ImageVector
    get() {
        if (_ArrowDownload != null) {
            return _ArrowDownload!!
        }
        _ArrowDownload = ImageVector.Builder(
            name = "ArrowDownload",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(5.25f, 20.5f)
                horizontalLineToRelative(13.498f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.101f, 1.493f)
                lineToRelative(-0.101f, 0.007f)
                lineTo(5.25f, 22f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.102f, -1.494f)
                lineToRelative(0.102f, -0.006f)
                horizontalLineToRelative(13.498f)
                lineTo(5.25f, 20.5f)
                close()
                moveTo(11.883f, 2.002f)
                lineTo(12f, 1.995f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.993f, 0.883f)
                lineToRelative(0.007f, 0.117f)
                verticalLineToRelative(12.59f)
                lineToRelative(3.294f, -3.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.32f, -0.083f)
                lineToRelative(0.094f, 0.084f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.083f, 1.32f)
                lineToRelative(-0.083f, 0.094f)
                lineToRelative(-4.997f, 4.996f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.32f, 0.084f)
                lineToRelative(-0.094f, -0.083f)
                lineToRelative(-5.004f, -4.997f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.32f, -1.498f)
                lineToRelative(0.094f, 0.083f)
                lineTo(11f, 15.58f)
                lineTo(11f, 2.995f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.883f, -0.993f)
                lineTo(12f, 1.995f)
                lineToRelative(-0.117f, 0.007f)
                close()
            }
        }.build()

        return _ArrowDownload!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowDownload: ImageVector? = null

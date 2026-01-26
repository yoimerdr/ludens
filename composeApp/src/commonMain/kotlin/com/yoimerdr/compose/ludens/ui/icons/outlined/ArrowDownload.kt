package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Arrow Download icon.
 *
 * This icon is a transformation of the outlined `Arrow Download` icon from Fluent Icons.
 */
val LudensIcons.Outlined.ArrowDownload: ImageVector
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
                moveTo(18.25f, 20.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, 1.5f)
                lineToRelative(-13f, 0.004f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, -1.5f)
                lineToRelative(13f, -0.004f)
                close()
                moveTo(11.648f, 2.012f)
                lineToRelative(0.102f, -0.007f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.743f, 0.648f)
                lineToRelative(0.007f, 0.102f)
                lineToRelative(-0.001f, 13.685f)
                lineToRelative(3.722f, -3.72f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.976f, -0.073f)
                lineToRelative(0.085f, 0.073f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.072f, 0.976f)
                lineToRelative(-0.073f, 0.084f)
                lineToRelative(-4.997f, 4.997f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.976f, 0.073f)
                lineToRelative(-0.085f, -0.073f)
                lineToRelative(-5.003f, -4.996f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.976f, -1.134f)
                lineToRelative(0.084f, 0.072f)
                lineToRelative(3.719f, 3.714f)
                lineTo(11f, 2.755f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.648f, -0.743f)
                lineToRelative(0.102f, -0.007f)
                lineToRelative(-0.102f, 0.007f)
                close()
            }
        }.build()

        return _ArrowDownload!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowDownload: ImageVector? = null

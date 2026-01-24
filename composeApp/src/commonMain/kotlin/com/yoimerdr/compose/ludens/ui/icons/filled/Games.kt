package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Games icon.
 *
 * This icon is a transformation of the filled `Games` icon from Fluent Icons.
 */
val LudensIcons.Filled.Games: ImageVector
    get() {
        if (_Games != null) {
            return _Games!!
        }
        _Games = ImageVector.Builder(
            name = "Games",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(14.998f, 5f)
                arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.24f, 13.996f)
                lineToRelative(-0.24f, 0.004f)
                lineTo(9.002f, 19f)
                arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.24f, -13.996f)
                lineTo(9.001f, 5f)
                horizontalLineToRelative(5.996f)
                close()
                moveTo(14.75f, 12.5f)
                arcToRelative(1.25f, 1.25f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 2.5f)
                arcToRelative(1.25f, 1.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2.5f)
                close()
                moveTo(8f, 9f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.743f, 0.648f)
                lineToRelative(-0.007f, 0.102f)
                verticalLineToRelative(1.498f)
                horizontalLineToRelative(-1.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.102f, 1.494f)
                lineToRelative(0.102f, 0.007f)
                lineToRelative(1.5f, -0.001f)
                verticalLineToRelative(1.502f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.493f, 0.102f)
                lineToRelative(0.007f, -0.102f)
                verticalLineToRelative(-1.502f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.102f, -1.492f)
                lineToRelative(-0.102f, -0.007f)
                lineToRelative(-1.5f, -0.001f)
                lineTo(8.75f, 9.75f)
                arcTo(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8f, 9f)
                close()
                moveTo(16.75f, 9f)
                arcToRelative(1.25f, 1.25f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 2.5f)
                arcToRelative(1.25f, 1.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2.5f)
                close()
            }
        }.build()

        return _Games!!
    }

@Suppress("ObjectPropertyName")
private var _Games: ImageVector? = null

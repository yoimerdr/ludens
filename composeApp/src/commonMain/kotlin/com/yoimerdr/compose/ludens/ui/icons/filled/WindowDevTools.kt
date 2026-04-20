package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Window Dev Tools icon.
 *
 * This icon is a transformation of the filled `Window Dev Tools` icon from Fluent Icons.
 */
val LudensIcons.Filled.WindowDevTools: ImageVector
    get() {
        if (_WindowDevTools != null) {
            return _WindowDevTools!!
        }
        _WindowDevTools = ImageVector.Builder(
            name = "WindowDevTools",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(3f, 3.05f)
                curveToRelative(-0.619f, 0.632f, -1f, 1.496f, -1f, 2.45f)
                verticalLineToRelative(11f)
                arcTo(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.5f, 20f)
                horizontalLineToRelative(7.014f)
                curveToRelative(0.051f, -0.252f, 0.144f, -0.5f, 0.28f, -0.736f)
                lineToRelative(0.73f, -1.264f)
                lineTo(5.5f, 18f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 16.5f)
                lineTo(4f, 7f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(1.254f)
                arcToRelative(4.515f, 4.515f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, -0.245f)
                lineTo(20f, 5.5f)
                curveToRelative(0f, -0.954f, -0.381f, -1.818f, -1f, -2.45f)
                lineTo(19f, 3f)
                horizontalLineToRelative(-0.05f)
                arcToRelative(3.489f, 3.489f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.45f, -1f)
                horizontalLineToRelative(-11f)
                curveToRelative(-0.954f, 0f, -1.818f, 0.381f, -2.45f, 1f)
                lineTo(3f, 3f)
                verticalLineToRelative(0.05f)
                close()
                moveTo(19.212f, 9f)
                arcToRelative(3.496f, 3.496f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.96f, 0.044f)
                lineToRelative(-1.651f, 2.858f)
                arcToRelative(1.167f, 1.167f, 0f, isMoreThanHalf = true, isPositiveArc = false, 2.02f, 1.167f)
                lineToRelative(1.651f, -2.859f)
                arcToRelative(3.501f, 3.501f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.975f, 5.762f)
                lineToRelative(-3.031f, 5.25f)
                arcToRelative(1.458f, 1.458f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.527f, -1.458f)
                lineToRelative(3.026f, -5.24f)
                arcTo(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.212f, 9f)
                close()
                moveTo(10.302f, 9.243f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.045f, 1.06f)
                lineTo(7.86f, 12.5f)
                lineToRelative(2.397f, 2.197f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.014f, 1.106f)
                lineToRelative(-3f, -2.75f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.106f)
                lineToRelative(3f, -2.75f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.06f, 0.046f)
                close()
                moveTo(13.257f, 15.803f)
                lineTo(15.277f, 13.951f)
                arcToRelative(4.495f, 4.495f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.008f, -2.91f)
                lineToRelative(-2.012f, -1.844f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.014f, 1.106f)
                lineTo(14.64f, 12.5f)
                lineToRelative(-2.397f, 2.197f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.014f, 1.106f)
                close()
            }
        }.build()

        return _WindowDevTools!!
    }

@Suppress("ObjectPropertyName")
private var _WindowDevTools: ImageVector? = null

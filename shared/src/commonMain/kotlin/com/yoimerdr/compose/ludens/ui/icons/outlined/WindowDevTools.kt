package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Window Dev Tools icon.
 *
 * This icon is a transformation of the outlined `Window Dev Tools` icon from Fluent Icons.
 */
val LudensIcons.Outlined.WindowDevTools: ImageVector
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
                moveTo(2f, 5.25f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.25f, 2f)
                horizontalLineToRelative(11.5f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 20f, 5.25f)
                verticalLineToRelative(2.76f)
                arcToRelative(4.508f, 4.508f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.5f, 0.096f)
                lineTo(18.5f, 7f)
                horizontalLineToRelative(-15f)
                verticalLineToRelative(9.75f)
                curveToRelative(0f, 0.966f, 0.784f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(7.985f)
                lineToRelative(-0.441f, 0.764f)
                arcToRelative(2.457f, 2.457f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.28f, 0.736f)
                lineTo(5.25f, 20f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 16.75f)
                lineTo(2f, 5.25f)
                close()
                moveTo(5.25f, 3.5f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.5f, 5.25f)
                verticalLineToRelative(0.25f)
                horizontalLineToRelative(15f)
                verticalLineToRelative(-0.25f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.75f, -1.75f)
                lineTo(5.25f, 3.5f)
                close()
                moveTo(19.857f, 9f)
                arcToRelative(3.496f, 3.496f, 0f, isMoreThanHalf = false, isPositiveArc = false, -3.356f, 1.736f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.184f, 3.788f)
                lineToRelative(-3.025f, 5.24f)
                arcToRelative(1.459f, 1.459f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.526f, 1.458f)
                lineToRelative(3.03f, -5.25f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.976f, -5.761f)
                lineToRelative(-1.65f, 2.858f)
                arcToRelative(1.167f, 1.167f, 0f, isMoreThanHalf = true, isPositiveArc = true, -2.021f, -1.167f)
                lineToRelative(1.65f, -2.858f)
                arcTo(3.478f, 3.478f, 0f, isMoreThanHalf = false, isPositiveArc = false, 19.857f, 9f)
                close()
                moveTo(10.303f, 9.243f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.046f, 1.06f)
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

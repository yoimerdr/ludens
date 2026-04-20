package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Apps icon.
 *
 * This icon is a transformation of the outlined `Apps` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Apps: ImageVector
    get() {
        if (_Apps != null) {
            return _Apps!!
        }
        _Apps = ImageVector.Builder(
            name = "Apps",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(18.492f, 2.33f)
                lineToRelative(3.179f, 3.179f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 3.182f)
                lineToRelative(-2.584f, 2.584f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21f, 13.5f)
                verticalLineToRelative(5.25f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.75f, 21f)
                lineTo(5.25f, 21f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 18.75f)
                lineTo(3f, 5.25f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.25f, 3f)
                horizontalLineToRelative(5.25f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.225f, 1.915f)
                lineTo(15.31f, 2.33f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.182f, 0f)
                close()
                moveTo(4.5f, 18.75f)
                curveToRelative(0f, 0.414f, 0.336f, 0.75f, 0.75f, 0.75f)
                lineToRelative(5.999f, -0.001f)
                lineToRelative(0.001f, -6.75f)
                lineTo(4.5f, 12.749f)
                verticalLineToRelative(6f)
                close()
                moveTo(12.749f, 19.499f)
                horizontalLineToRelative(6.001f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.75f, -0.75f)
                lineTo(19.5f, 13.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                horizontalLineToRelative(-6.001f)
                verticalLineToRelative(6.75f)
                close()
                moveTo(10.5f, 4.499f)
                lineTo(5.25f, 4.499f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, 0.75f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6.75f)
                verticalLineToRelative(-6f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                close()
                moveTo(12.75f, 9.309f)
                verticalLineToRelative(1.94f)
                horizontalLineToRelative(1.94f)
                lineToRelative(-1.94f, -1.94f)
                close()
                moveTo(16.37f, 3.391f)
                lineTo(13.192f, 6.569f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1.061f)
                lineToRelative(3.179f, 3.179f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.06f, 0f)
                lineToRelative(3.18f, -3.179f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -1.06f)
                lineToRelative(-3.18f, -3.18f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.06f, 0f)
                close()
            }
        }.build()

        return _Apps!!
    }

@Suppress("ObjectPropertyName")
private var _Apps: ImageVector? = null

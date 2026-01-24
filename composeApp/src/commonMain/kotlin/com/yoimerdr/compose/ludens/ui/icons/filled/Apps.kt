package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Apps icon.
 *
 * This icon is a transformation of the filled `Apps` icon from Fluent Icons.
 */
val LudensIcons.Filled.Apps: ImageVector
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
                lineToRelative(-2.423f, 2.422f)
                arcTo(2.501f, 2.501f, 0f, isMoreThanHalf = false, isPositiveArc = true, 21f, 13.5f)
                verticalLineToRelative(5f)
                arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.5f, 2.5f)
                horizontalLineToRelative(-13f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 18.5f)
                verticalLineToRelative(-13f)
                arcTo(2.5f, 2.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.5f, 3f)
                horizontalLineToRelative(5f)
                curveToRelative(1.121f, 0f, 2.07f, 0.737f, 2.387f, 1.754f)
                lineTo(15.31f, 2.33f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.182f, 0f)
                close()
                moveTo(11f, 13f)
                lineTo(5f, 13f)
                verticalLineToRelative(5.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.5f, 0.5f)
                lineTo(11f, 19f)
                verticalLineToRelative(-6f)
                close()
                moveTo(18.5f, 13f)
                lineTo(13f, 13f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(5.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.5f, -0.5f)
                verticalLineToRelative(-5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, -0.5f)
                close()
                moveTo(14.44f, 10.999f)
                lineTo(13f, 9.559f)
                verticalLineToRelative(1.44f)
                horizontalLineToRelative(1.44f)
                close()
                moveTo(10.5f, 4.999f)
                horizontalLineToRelative(-5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, 0.5f)
                lineTo(5f, 11f)
                horizontalLineToRelative(6f)
                lineTo(11f, 5.5f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.5f, -0.5f)
                close()
            }
        }.build()

        return _Apps!!
    }

@Suppress("ObjectPropertyName")
private var _Apps: ImageVector? = null

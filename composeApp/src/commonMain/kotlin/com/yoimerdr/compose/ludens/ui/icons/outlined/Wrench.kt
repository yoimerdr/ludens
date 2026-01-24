package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Wrench icon.
 *
 * This icon is a transformation of the outlined `Wrench` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Wrench: ImageVector
    get() {
        if (_Wrench != null) {
            return _Wrench!!
        }
        _Wrench = ImageVector.Builder(
            name = "Wrench",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(10.5f, 7.751f)
                arcToRelative(5.75f, 5.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.38f, -5.114f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.186f, 1.197f)
                lineTo(16.301f, 6.6f)
                lineToRelative(1.06f, 1.06f)
                lineToRelative(2.779f, -2.778f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.193f, 0.179f)
                arcToRelative(5.75f, 5.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -6.422f, 8.284f)
                lineToRelative(-7.365f, 7.618f)
                arcToRelative(3.05f, 3.05f, 0f, isMoreThanHalf = false, isPositiveArc = true, -4.387f, -4.24f)
                lineToRelative(7.475f, -7.734f)
                arcToRelative(5.766f, 5.766f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.134f, -1.238f)
                close()
                moveTo(16.25f, 3.501f)
                arcToRelative(4.25f, 4.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.067f, 5.489f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.178f, 0.74f)
                lineToRelative(-7.768f, 8.035f)
                arcToRelative(1.55f, 1.55f, 0f, isMoreThanHalf = true, isPositiveArc = false, 2.23f, 2.156f)
                lineToRelative(7.676f, -7.941f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.775f, -0.191f)
                arcToRelative(4.25f, 4.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.466f, -5.03f)
                lineToRelative(-2.492f, 2.492f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.061f, 0f)
                lineTo(14.71f, 7.13f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -1.06f)
                lineToRelative(2.466f, -2.467f)
                arcToRelative(4.268f, 4.268f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.926f, -0.102f)
                close()
            }
        }.build()

        return _Wrench!!
    }

@Suppress("ObjectPropertyName")
private var _Wrench: ImageVector? = null

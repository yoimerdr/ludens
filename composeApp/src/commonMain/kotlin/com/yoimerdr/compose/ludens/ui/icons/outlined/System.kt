package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * System icon.
 *
 * This icon is a transformation of the outlined `Desktop` icon from Fluent Icons.
 */
val LudensIcons.Outlined.System: ImageVector
    get() {
        if (_System != null) {
            return _System!!
        }
        _System = ImageVector.Builder(
            name = "System",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(4.75f, 5f)
                horizontalLineToRelative(14.5f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 7.75f)
                verticalLineToRelative(8.5f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.25f, 19f)
                lineTo(4.75f, 19f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 16.25f)
                verticalLineToRelative(-8.5f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.75f, 5f)
                close()
                moveTo(4.75f, 6.5f)
                curveToRelative(-0.69f, 0f, -1.25f, 0.56f, -1.25f, 1.25f)
                verticalLineToRelative(8.5f)
                curveToRelative(0f, 0.69f, 0.56f, 1.25f, 1.25f, 1.25f)
                horizontalLineToRelative(14.5f)
                curveToRelative(0.69f, 0f, 1.25f, -0.56f, 1.25f, -1.25f)
                verticalLineToRelative(-8.5f)
                curveToRelative(0f, -0.69f, -0.56f, -1.25f, -1.25f, -1.25f)
                lineTo(4.75f, 6.5f)
                close()
            }
        }.build()

        return _System!!
    }

@Suppress("ObjectPropertyName")
private var _System: ImageVector? = null

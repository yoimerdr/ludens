package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Save icon.
 *
 * This icon is a transformation of the outlined `Save` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Save: ImageVector
    get() {
        if (_Save != null) {
            return _Save!!
        }
        _Save = ImageVector.Builder(
            name = "Save",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(3f, 5.75f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.75f, 3f)
                horizontalLineToRelative(9.964f)
                arcToRelative(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.299f, 0.952f)
                lineToRelative(2.035f, 2.035f)
                curveToRelative(0.61f, 0.61f, 0.952f, 1.437f, 0.952f, 2.299f)
                verticalLineToRelative(9.964f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.25f, 21f)
                lineTo(5.75f, 21f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 18.25f)
                lineTo(3f, 5.75f)
                close()
                moveTo(5.75f, 4.5f)
                curveToRelative(-0.69f, 0f, -1.25f, 0.56f, -1.25f, 1.25f)
                verticalLineToRelative(12.5f)
                curveToRelative(0f, 0.69f, 0.56f, 1.25f, 1.25f, 1.25f)
                lineTo(6f, 19.5f)
                verticalLineToRelative(-5.25f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.25f, 12f)
                horizontalLineToRelative(7.5f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18f, 14.25f)
                verticalLineToRelative(5.25f)
                horizontalLineToRelative(0.25f)
                curveToRelative(0.69f, 0f, 1.25f, -0.56f, 1.25f, -1.25f)
                lineTo(19.5f, 8.286f)
                curveToRelative(0f, -0.465f, -0.184f, -0.91f, -0.513f, -1.238f)
                lineToRelative(-2.035f, -2.035f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.952f, -0.49f)
                lineTo(16f, 7.25f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.25f, 2.25f)
                horizontalLineToRelative(-4.5f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7f, 7.25f)
                lineTo(7f, 4.5f)
                lineTo(5.75f, 4.5f)
                close()
                moveTo(16.5f, 19.5f)
                verticalLineToRelative(-5.25f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                horizontalLineToRelative(-7.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, 0.75f)
                verticalLineToRelative(5.25f)
                horizontalLineToRelative(9f)
                close()
                moveTo(8.5f, 4.5f)
                verticalLineToRelative(2.75f)
                curveToRelative(0f, 0.414f, 0.336f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(4.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.75f, -0.75f)
                lineTo(14.5f, 4.5f)
                horizontalLineToRelative(-6f)
                close()
            }
        }.build()

        return _Save!!
    }

@Suppress("ObjectPropertyName")
private var _Save: ImageVector? = null

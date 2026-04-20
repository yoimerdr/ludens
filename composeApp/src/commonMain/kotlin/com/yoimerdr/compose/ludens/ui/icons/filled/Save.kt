package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Save icon.
 *
 * This icon is a transformation of the filled `Save` icon from Fluent Icons.
 */
val LudensIcons.Filled.Save: ImageVector
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
                moveTo(6.75f, 3f)
                horizontalLineToRelative(-1f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3f, 5.75f)
                verticalLineToRelative(12.5f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.75f, 21f)
                horizontalLineTo(6f)
                verticalLineToRelative(-6f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.25f, -2.25f)
                horizontalLineToRelative(7.5f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18f, 15f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(0.25f)
                arcTo(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 21f, 18.25f)
                verticalLineTo(8.286f)
                arcToRelative(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.952f, -2.299f)
                lineToRelative(-2.035f, -2.035f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15.75f, 3f)
                verticalLineToRelative(4.5f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.25f, 2.25f)
                horizontalLineTo(9f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.75f, 7.5f)
                verticalLineTo(3f)
                close()
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(14.25f, 3f)
                verticalLineToRelative(4.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.75f, 0.75f)
                horizontalLineTo(9f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.75f, -0.75f)
                verticalLineTo(3f)
                horizontalLineToRelative(6f)
                close()
                moveTo(16.5f, 21f)
                verticalLineToRelative(-6f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, -0.75f)
                horizontalLineToRelative(-7.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, 0.75f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(9f)
                close()
            }
        }.build()

        return _Save!!
    }

@Suppress("ObjectPropertyName")
private var _Save: ImageVector? = null

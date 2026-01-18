package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Apps list icon.
 *
 * This icon is a transformation of the filled `Apps list` icon from Fluent Icons.
 */
val AppsList: ImageVector
    get() {
        if (_AppsList != null) {
            return _AppsList!!
        }
        _AppsList = ImageVector.Builder(
            name = "AppsList",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(6.248f, 16.002f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(2.498f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.248f, 22f)
                lineTo(3.75f, 22f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 20.25f)
                verticalLineToRelative(-2.498f)
                curveToRelative(0f, -0.966f, 0.784f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(2.498f)
                close()
                moveTo(9.748f, 18f)
                horizontalLineToRelative(11.505f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.102f, 1.493f)
                lineToRelative(-0.102f, 0.007f)
                lineTo(9.748f, 19.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.102f, -1.493f)
                lineTo(9.748f, 18f)
                horizontalLineToRelative(11.505f)
                lineTo(9.748f, 18f)
                close()
                moveTo(6.248f, 9.001f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(2.498f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                lineTo(3.75f, 14.999f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 13.249f)
                lineTo(2f, 10.75f)
                curveToRelative(0f, -0.966f, 0.784f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(2.498f)
                close()
                moveTo(9.748f, 11f)
                horizontalLineToRelative(11.505f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.102f, 1.493f)
                lineToRelative(-0.102f, 0.007f)
                lineTo(9.748f, 12.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.102f, -1.493f)
                lineTo(9.748f, 11f)
                horizontalLineToRelative(11.505f)
                lineTo(9.748f, 11f)
                close()
                moveTo(6.248f, 2f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(2.498f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.75f, 1.75f)
                lineTo(3.75f, 7.998f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 6.248f)
                lineTo(2f, 3.75f)
                curveTo(2f, 2.784f, 2.784f, 2f, 3.75f, 2f)
                horizontalLineToRelative(2.498f)
                close()
                moveTo(9.748f, 4f)
                horizontalLineToRelative(11.505f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.102f, 1.493f)
                lineToRelative(-0.102f, 0.007f)
                lineTo(9.748f, 5.5f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.102f, -1.493f)
                lineTo(9.748f, 4f)
                horizontalLineToRelative(11.505f)
                lineTo(9.748f, 4f)
                close()
            }
        }.build()

        return _AppsList!!
    }

@Suppress("ObjectPropertyName")
private var _AppsList: ImageVector? = null

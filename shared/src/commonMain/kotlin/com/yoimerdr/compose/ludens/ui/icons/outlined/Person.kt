package com.yoimerdr.compose.ludens.ui.icons.outlined

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Person icon.
 *
 * This icon is a transformation of the outlined `Person` icon from Fluent Icons.
 */
val LudensIcons.Outlined.Person: ImageVector
    get() {
        if (_Person != null) {
            return _Person!!
        }
        _Person = ImageVector.Builder(
            name = "Person",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(17.754f, 14f)
                arcToRelative(2.249f, 2.249f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.25f, 2.249f)
                verticalLineToRelative(0.575f)
                curveToRelative(0f, 0.894f, -0.32f, 1.76f, -0.902f, 2.438f)
                curveToRelative(-1.57f, 1.834f, -3.957f, 2.739f, -7.102f, 2.739f)
                curveToRelative(-3.146f, 0f, -5.532f, -0.905f, -7.098f, -2.74f)
                arcToRelative(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.898f, -2.435f)
                verticalLineToRelative(-0.577f)
                arcToRelative(2.249f, 2.249f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.249f, -2.25f)
                horizontalLineToRelative(11.501f)
                close()
                moveTo(17.754f, 15.5f)
                lineTo(6.253f, 15.5f)
                arcToRelative(0.749f, 0.749f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.75f, 0.749f)
                verticalLineToRelative(0.577f)
                curveToRelative(0f, 0.536f, 0.192f, 1.054f, 0.54f, 1.461f)
                curveToRelative(1.253f, 1.468f, 3.219f, 2.214f, 5.957f, 2.214f)
                reflectiveCurveToRelative(4.706f, -0.746f, 5.962f, -2.214f)
                arcToRelative(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.541f, -1.463f)
                verticalLineToRelative(-0.575f)
                arcToRelative(0.749f, 0.749f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.749f, -0.75f)
                close()
                moveTo(12f, 2.004f)
                arcToRelative(5f, 5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, 10f)
                arcToRelative(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -10f)
                close()
                moveTo(12f, 3.504f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 7f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -7f)
                close()
            }
        }.build()

        return _Person!!
    }

@Suppress("ObjectPropertyName")
private var _Person: ImageVector? = null

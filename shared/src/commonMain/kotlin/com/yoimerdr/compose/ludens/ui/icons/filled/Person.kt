package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Person icon.
 *
 * This icon is a transformation of the filled `Person` icon from Fluent Icons.
 */
val LudensIcons.Filled.Person: ImageVector
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
                verticalLineToRelative(0.918f)
                arcToRelative(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.513f, 1.599f)
                curveTo(17.945f, 20.929f, 15.42f, 22f, 12f, 22f)
                curveToRelative(-3.422f, 0f, -5.945f, -1.072f, -7.487f, -3.237f)
                arcToRelative(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.51f, -1.595f)
                verticalLineToRelative(-0.92f)
                arcToRelative(2.249f, 2.249f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.249f, -2.25f)
                horizontalLineToRelative(11.501f)
                close()
                moveTo(12f, 2.004f)
                arcToRelative(5f, 5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, 10f)
                arcToRelative(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -10f)
                close()
            }
        }.build()

        return _Person!!
    }

@Suppress("ObjectPropertyName")
private var _Person: ImageVector? = null

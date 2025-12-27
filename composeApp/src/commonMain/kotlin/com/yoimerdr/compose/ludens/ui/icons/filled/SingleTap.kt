package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Single Tap icon.
 *
 * This icon is a transformation of the filled `Tap Single` icon from Fluent Icons.
 */
val LudensIcons.Filled.SingleTap: ImageVector
    get() {
        if (_SingleTap != null) {
            return _SingleTap!!
        }
        _SingleTap = ImageVector.Builder(
            name = "Filled.SingleTap",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(11.749f, 6f)
                curveToRelative(1.358f, 0f, 1.952f, 0.898f, 2f, 2.326f)
                lineToRelative(0.003f, 0.189f)
                lineTo(13.752f, 11f)
                lineToRelative(2.871f, 0.422f)
                arcToRelative(2.75f, 2.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.292f, 3.284f)
                lineToRelative(-0.04f, 0.17f)
                lineToRelative(-1.228f, 4.442f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.292f, 1.239f)
                lineToRelative(-0.146f, 0.027f)
                lineToRelative(-3.061f, 0.44f)
                arcToRelative(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.825f, -0.973f)
                lineToRelative(-0.06f, -0.14f)
                lineToRelative(-0.217f, -0.572f)
                arcToRelative(4.13f, 4.13f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.176f, -1.674f)
                lineToRelative(-0.203f, -0.163f)
                lineToRelative(-1.596f, -1.2f)
                arcToRelative(1.748f, 1.748f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.266f, -0.166f)
                lineToRelative(-0.143f, -0.064f)
                lineToRelative(-2.195f, -0.868f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.474f, -0.66f)
                curveToRelative(-0.036f, -0.722f, 0.49f, -1.246f, 1.422f, -1.712f)
                curveToRelative(0.719f, -0.36f, 1.727f, -0.33f, 3.066f, 0.043f)
                lineToRelative(0.272f, 0.08f)
                verticalLineToRelative(-4.45f)
                curveToRelative(0f, -1.538f, 0.578f, -2.506f, 1.996f, -2.506f)
                close()
                moveTo(11.749f, 2.5f)
                arcToRelative(5.75f, 5.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.3f, 7.987f)
                lineToRelative(0.173f, 0.042f)
                arcToRelative(3.763f, 3.763f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.453f, -0.095f)
                lineToRelative(-1.267f, -0.186f)
                arcToRelative(4.25f, 4.25f, 0f, isMoreThanHalf = true, isPositiveArc = false, -6.748f, 1.018f)
                lineToRelative(-0.001f, 0.415f)
                curveToRelative(-0.614f, -0.109f, -1.168f, -0.136f, -1.672f, -0.072f)
                arcTo(5.75f, 5.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11.75f, 2.5f)
                close()
            }
        }.build()

        return _SingleTap!!
    }

@Suppress("ObjectPropertyName")
private var _SingleTap: ImageVector? = null

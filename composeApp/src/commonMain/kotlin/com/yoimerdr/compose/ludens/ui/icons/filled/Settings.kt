package com.yoimerdr.compose.ludens.ui.icons.filled

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.icons.LudensIcons

/**
 * Settings icon.
 *
 * This icon is a transformation of the filled `Settings` icon from Fluent Icons.
 */
val LudensIcons.Filled.Settings: ImageVector
    get() {
        if (_Settings != null) {
            return _Settings!!
        }
        _Settings = ImageVector.Builder(
            name = "Filled.Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(12.012f, 2.25f)
                curveToRelative(0.734f, 0.008f, 1.465f, 0.093f, 2.182f, 0.253f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.582f, 0.649f)
                lineToRelative(0.17f, 1.527f)
                arcToRelative(1.384f, 1.384f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.927f, 1.116f)
                lineToRelative(1.401f, -0.615f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.85f, 0.174f)
                arcToRelative(9.792f, 9.792f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.204f, 3.792f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.271f, 0.825f)
                lineToRelative(-1.242f, 0.916f)
                arcToRelative(1.381f, 1.381f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 2.226f)
                lineToRelative(1.243f, 0.915f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.272f, 0.826f)
                arcToRelative(9.797f, 9.797f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.204f, 3.792f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.848f, 0.175f)
                lineToRelative(-1.407f, -0.617f)
                arcToRelative(1.38f, 1.38f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.926f, 1.114f)
                lineToRelative(-0.169f, 1.526f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.572f, 0.647f)
                arcToRelative(9.518f, 9.518f, 0f, isMoreThanHalf = false, isPositiveArc = true, -4.406f, 0f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.572f, -0.647f)
                lineToRelative(-0.168f, -1.524f)
                arcToRelative(1.382f, 1.382f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.926f, -1.11f)
                lineToRelative(-1.406f, 0.616f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.849f, -0.175f)
                arcToRelative(9.798f, 9.798f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.204f, -3.796f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.272f, -0.826f)
                lineToRelative(1.243f, -0.916f)
                arcToRelative(1.38f, 1.38f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -2.226f)
                lineToRelative(-1.243f, -0.914f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.271f, -0.826f)
                arcToRelative(9.793f, 9.793f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.204f, -3.792f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.85f, -0.174f)
                lineToRelative(1.4f, 0.615f)
                arcToRelative(1.387f, 1.387f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.93f, -1.118f)
                lineToRelative(0.17f, -1.526f)
                arcToRelative(0.75f, 0.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.583f, -0.65f)
                curveToRelative(0.717f, -0.159f, 1.45f, -0.243f, 2.201f, -0.252f)
                close()
                moveTo(12f, 9f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 6f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -6f)
                close()
            }
        }.build()

        return _Settings!!
    }

@Suppress("ObjectPropertyName")
private var _Settings: ImageVector? = null

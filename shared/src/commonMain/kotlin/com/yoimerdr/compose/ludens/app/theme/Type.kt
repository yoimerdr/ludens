package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Suppress("ObjectPropertyName")
private var _Typography: Typography? = null

private fun displayStyle(
    fontFamily: FontFamily,
    fontSize: Int,
    lineHeight: Int,
    weight: FontWeight,
    letterSpacing: Float,
) = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize.sp,
    lineHeight = lineHeight.sp,
    fontWeight = weight,
    letterSpacing = letterSpacing.sp,
)

private fun bodyStyle(
    fontFamily: FontFamily,
    fontSize: Int,
    lineHeight: Int,
    weight: FontWeight,
    letterSpacing: Float,
) = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize.sp,
    lineHeight = lineHeight.sp,
    fontWeight = weight,
    letterSpacing = letterSpacing.sp,
)

val Typography: Typography
    @Stable
    @Composable
    get() {
        if (_Typography == null) {
            val displayFont = DisplayFontFamily
            val bodyFont = BodyFontFamily
            _Typography = Typography(
                displayLarge = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 56,
                    lineHeight = 60,
                    weight = FontWeight.SemiBold,
                    letterSpacing = -0.28f,
                ),
                displayMedium = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 40,
                    lineHeight = 44,
                    weight = FontWeight.SemiBold,
                    letterSpacing = 0f,
                ),
                displaySmall = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 28,
                    lineHeight = 32,
                    weight = FontWeight.Normal,
                    letterSpacing = 0.196f,
                ),
                headlineLarge = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 34,
                    lineHeight = 50,
                    weight = FontWeight.SemiBold,
                    letterSpacing = -0.374f,
                ),
                headlineMedium = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 24,
                    lineHeight = 36,
                    weight = FontWeight.Light,
                    letterSpacing = 0f,
                ),
                headlineSmall = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 21,
                    lineHeight = 25,
                    weight = FontWeight.Normal,
                    letterSpacing = 0.231f,
                ),
                titleLarge = displayStyle(
                    fontFamily = displayFont,
                    fontSize = 21,
                    lineHeight = 25,
                    weight = FontWeight.Bold,
                    letterSpacing = 0.231f,
                ),
                titleMedium = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 17,
                    lineHeight = 21,
                    weight = FontWeight.SemiBold,
                    letterSpacing = -0.374f,
                ),
                titleSmall = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 14,
                    lineHeight = 18,
                    weight = FontWeight.SemiBold,
                    letterSpacing = -0.224f,
                ),
                bodyLarge = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 17,
                    lineHeight = 25,
                    weight = FontWeight.Normal,
                    letterSpacing = -0.374f,
                ),
                bodyMedium = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 14,
                    lineHeight = 20,
                    weight = FontWeight.Normal,
                    letterSpacing = -0.224f,
                ),
                bodySmall = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 12,
                    lineHeight = 16,
                    weight = FontWeight.Normal,
                    letterSpacing = -0.12f,
                ),
                labelLarge = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 17,
                    lineHeight = 20,
                    weight = FontWeight.Normal,
                    letterSpacing = 0f,
                ),
                labelMedium = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 14,
                    lineHeight = 18,
                    weight = FontWeight.Normal,
                    letterSpacing = -0.224f,
                ),
                labelSmall = bodyStyle(
                    fontFamily = bodyFont,
                    fontSize = 12,
                    lineHeight = 16,
                    weight = FontWeight.SemiBold,
                    letterSpacing = -0.12f,
                ),
            )
        }
        return _Typography!!
    }

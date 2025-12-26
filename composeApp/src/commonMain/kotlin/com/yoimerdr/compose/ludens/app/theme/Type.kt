package com.yoimerdr.compose.ludens.app.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Suppress("ObjectPropertyName")
var _Typography: Typography? = null

val Typography: Typography
    @Stable
    @Composable
    get() {
        if (_Typography == null) {
            val baseline = Typography()

            _Typography = Typography(
                displayLarge = baseline.displayLarge.copy(fontFamily = Outfit),
                displayMedium = baseline.displayMedium.copy(fontFamily = Outfit),
                displaySmall = baseline.displaySmall.copy(fontFamily = Outfit),
                headlineLarge = baseline.headlineLarge.copy(fontFamily = Outfit),
                headlineMedium = baseline.headlineMedium.copy(fontFamily = Outfit),
                headlineSmall = baseline.headlineSmall.copy(fontFamily = Outfit),
                titleLarge = baseline.titleLarge.copy(fontFamily = Outfit),
                titleMedium = baseline.titleMedium.copy(fontFamily = Outfit),
                titleSmall = baseline.titleSmall.copy(fontFamily = Outfit),
                bodyLarge = baseline.bodyLarge.copy(fontFamily = Inter),
                bodyMedium = baseline.bodyMedium.copy(fontFamily = Inter),
                bodySmall = baseline.bodySmall.copy(fontFamily = Inter),
                labelLarge = baseline.labelLarge.copy(fontFamily = Inter),
                labelMedium = baseline.labelMedium.copy(fontFamily = Inter),
                labelSmall = baseline.labelSmall.copy(fontFamily = Inter),
            )
        }
        return _Typography!!
    }
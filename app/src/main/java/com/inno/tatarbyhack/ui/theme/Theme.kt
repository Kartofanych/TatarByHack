package com.inno.tatarbyhack.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Stable
class YandexTodoColors(
    supportSeparator: Color,
    supportOverlay: Color,
    labelPrimary: Color,
    labelSecondary: Color,
    labelTertiary: Color,
    labelDisable: Color,
    colorRed: Color,
    colorGreen: Color,
    colorOrange: Color,
    colorBlue: Color,
    colorGray: Color,
    colorNavItems: Color,
    colorWhite: Color,
    backPrimary: Color,
    backSecondary: Color,
    backElevated: Color,
){
    var supportSeparator by mutableStateOf(supportSeparator)
        private set
    var supportOverlay by mutableStateOf(supportOverlay)
        private set
    var labelPrimary by mutableStateOf(labelPrimary)
        private set
    var labelSecondary by mutableStateOf(labelSecondary)
        private set
    var labelTertiary by mutableStateOf(labelTertiary)
        private set
    var labelDisable by mutableStateOf(labelDisable)
        private set
    var colorRed by mutableStateOf(colorRed)
        private set
    var colorGreen by mutableStateOf(colorGreen)
        private set
    var colorBlue by mutableStateOf(colorBlue)
        private set
    var colorOrange by mutableStateOf(colorOrange)
        private set
    var colorGray by mutableStateOf(colorGray)
        private set
    var colorGrayLight by mutableStateOf(colorNavItems)
        private set
    var colorWhite by mutableStateOf(colorWhite)
        private set
    var backPrimary by mutableStateOf(backPrimary)
        private set
    var backSecondary by mutableStateOf(backSecondary)
        private set
    var backElevated by mutableStateOf(backElevated)
        private set

    fun update(other: YandexTodoColors){
        supportSeparator = other.supportSeparator
        supportOverlay = other.supportOverlay
        labelPrimary = other.labelPrimary
        labelSecondary = other.labelSecondary
        labelTertiary = other.labelTertiary
        labelDisable = other.labelDisable
        colorRed = other.colorRed
        colorGreen = other.colorGreen
        colorBlue = other.colorBlue
        colorOrange = other.colorOrange
        colorGray = other.colorGray
        colorGrayLight = other.colorGrayLight
        colorWhite = other.colorWhite
        backPrimary = other.backPrimary
        backSecondary = other.backSecondary
        backElevated = other.backElevated
    }

}

private val DarkColorScheme = YandexTodoColors(
    supportSeparator = Gray,
    supportOverlay = Blue,
    labelPrimary = Black,
    labelSecondary = White,
    labelTertiary = Gray,
    labelDisable = Gray,
    colorRed = Color.Red,
    colorGreen = Green,
    colorBlue = Blue,
    colorOrange = Orange,
    colorGray = Gray,
    colorNavItems = NavGray,
    colorWhite = White,
    backPrimary = Purple,
    backSecondary = DarkPurple,
    backElevated = LightPurple,
)


private val LightColorScheme = YandexTodoColors(
    supportSeparator = Gray,
    supportOverlay = Blue,
    labelPrimary = Black,
    labelSecondary = White,
    labelTertiary = Gray,
    labelDisable = Gray,
    colorRed = Color.Red,
    colorGreen = Green,
    colorBlue = Blue,
    colorOrange = Orange,
    colorGray = Gray,
    colorNavItems = NavGray,
    colorWhite = White,
    backPrimary = Purple,
    backSecondary = DarkPurple,
    backElevated = LightPurple,
)
@Composable
fun ProvideYandexTodoColors(
    colors: YandexTodoColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalCustomColors provides colorPalette, content = content)
}

internal val LocalCustomColors = staticCompositionLocalOf<YandexTodoColors> { error("No colors provided") }
object TatarTheme {
    val colors: YandexTodoColors
        @Composable
        get() = LocalCustomColors.current

}

@Composable
fun TatarByHackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            //WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    ProvideYandexTodoColors(colors = colorScheme) {
        MaterialTheme(
            content = content
        )
    }
}
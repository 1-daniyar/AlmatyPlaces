package com.example.almatyplaces.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1B6B4A), onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFA6F2C9), onPrimaryContainer = Color(0xFF002114),
    secondary = Color(0xFF4D6357), onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCFE9D9), onSecondaryContainer = Color(0xFF0A1F16),
    background = Color(0xFFF5FBF6), onBackground = Color(0xFF171D1A),
    surface = Color(0xFFF5FBF6), onSurface = Color(0xFF171D1A),
    surfaceVariant = Color(0xFFDBE5DD), onSurfaceVariant = Color(0xFF3F4943),
    outline = Color(0xFF6F7973),
    error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8BD6AE), onPrimary = Color(0xFF003824),
    primaryContainer = Color(0xFF005236), onPrimaryContainer = Color(0xFFA6F2C9),
    secondary = Color(0xFFB3CCBD), onSecondary = Color(0xFF1F352A),
    secondaryContainer = Color(0xFF354B40), onSecondaryContainer = Color(0xFFCFE9D9),
    background = Color(0xFF0F1512), onBackground = Color(0xFFDEE4DF),
    surface = Color(0xFF0F1512), onSurface = Color(0xFFDEE4DF),
    surfaceVariant = Color(0xFF3F4943), onSurfaceVariant = Color(0xFFBFC9C1),
    outline = Color(0xFF89938C),
    error = Color(0xFFFFB4AB), onError = Color(0xFF690005)
)

@Composable
fun AlmatyPlacesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
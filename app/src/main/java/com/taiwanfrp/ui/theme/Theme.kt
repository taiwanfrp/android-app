package com.taiwanfrp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SlatePrimary,
    onPrimary = Color.Black,
    background = SlateBackground,
    onBackground = Color.White,
    surface = SlateSurface,
    onSurface = Color.White,
    secondary = SlateSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// Blue Theme (Deep Sea)
private val BlueColorScheme = darkColorScheme(
    primary = DeepSeaPrimary,
    onPrimary = Color.Black,
    background = DeepSeaBackground,
    onBackground = Color.White,
    surface = DeepSeaSurface,
    onSurface = Color.White,
    secondary = DeepSeaSecondary
)

// Blue Theme (Light - 湖水藍)
private val BlueLightColorScheme = lightColorScheme(
    primary = DiscordBlue,
    onPrimary = Color.White,
    primaryContainer = DiscordLightBlue,
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF7289DA),
    onSecondary = Color.White,
    surface = Color(0xFFE3E5F1),
    onSurface = Color(0xFF23272A),
    onSurfaceVariant = Color(0xFF4F545C),
    background = Color(0xFFF0F2FF),
    onBackground = Color.Black
)

// Green Theme (Forest Green)
private val GreenColorScheme = darkColorScheme(
    primary = ForestPrimary,
    onPrimary = Color.Black,
    background = ForestBackground,
    onBackground = Color.White,
    surface = ForestSurface,
    onSurface = Color.White,
    secondary = ForestSecondary
)

// Green Theme (Light - 薄荷綠)
private val GreenLightColorScheme = lightColorScheme(
    primary = DarkEmeraldGreen,
    onPrimary = Color.White,
    primaryContainer = MintGreen,
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF58D68D),
    onSecondary = Color.Black,
    surface = Color(0xFFD5F5E3),
    onSurface = Color(0xFF145A32),
    onSurfaceVariant = Color(0xFF196F3D),
    background = Color(0xFFF0FFF4),
    onBackground = Color.Black
)

// OLED Black Theme
private val OledColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray,
    secondaryContainer = Color(0xFF333333),
    onSecondaryContainer = Color.White
)

// Pure White Theme (極致白)
private val WhiteColorScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Gray,
    outline = Color.Black,
    secondaryContainer = Color(0xFFEEEEEE),
    onSecondaryContainer = Color.Black
)

@Composable
fun TaiwamfrpTheme(
    theme: String = "System",
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = when (theme) {
        "Light" -> LightColorScheme
        "Dark" -> DarkColorScheme
        "Blue" -> BlueColorScheme
        "BlueLight" -> BlueLightColorScheme
        "Green" -> GreenColorScheme
        "GreenLight" -> GreenLightColorScheme
        "Oled" -> OledColorScheme
        "White" -> WhiteColorScheme
        else -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

package com.taiwanfrp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val FallbackLightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    secondary = Color(0xFF635A75),
    secondaryContainer = Color(0xFFE8DEF8),
    onSecondaryContainer = Color(0xFF1D192B),
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),
    surface = Color(0xFFFEF7FF),
    surfaceContainerLow = Color(0xFFF7F2FA),
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerHigh = Color(0xFFECE6F0),
    surfaceContainerHighest = Color(0xFFE6E0E9),
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    inverseSurface = Color(0xFF322F35),
    inverseOnSurface = Color(0xFFF5EFF7),
    inversePrimary = Color(0xFFD0BCFF),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

private val FallbackDarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = Color(0xFFCCC2DC),
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = Color(0xFFE8DEF8),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    surface = Color(0xFF141218),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
    onSurface = Color(0xFFE6E0E9),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    inverseSurface = Color(0xFFE6E0E9),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = Color(0xFF6750A4),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

// Blue Theme (Deep Sea)
private val BlueColorScheme = darkColorScheme(
    primary = DeepSeaPrimary,
    onPrimary = Color.Black,
    background = DeepSeaBackground,
    onBackground = Color.White,
    surface = DeepSeaSurface,
    onSurface = Color.White,
    secondary = DeepSeaSecondary,
    surfaceContainerHigh = Color(0xFF1C2128),
    surfaceContainerHighest = Color(0xFF22272E)
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
    onBackground = Color.Black,
    surfaceContainerHigh = Color(0xFFDCDFEF),
    surfaceContainerHighest = Color(0xFFD4D8EB)
)

// Green Theme (Forest Green)
private val GreenColorScheme = darkColorScheme(
    primary = ForestPrimary,
    onPrimary = Color.Black,
    background = ForestBackground,
    onBackground = Color.White,
    surface = ForestSurface,
    onSurface = Color.White,
    secondary = ForestSecondary,
    surfaceContainerHigh = Color(0xFF183328),
    surfaceContainerHighest = Color(0xFF1F4032)
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
    onBackground = Color.Black,
    surfaceContainerHigh = Color(0xFFC8EED7),
    surfaceContainerHighest = Color(0xFFBCE3CB)
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
    onSecondaryContainer = Color.White,
    surfaceContainerHigh = Color(0xFF111111),
    surfaceContainerHighest = Color(0xFF222222)
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
    onSecondaryContainer = Color.Black,
    surfaceContainerHigh = Color(0xFFF5F5F5),
    surfaceContainerHighest = Color(0xFFEBEBEB)
)

val ExpressiveShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(50)
)

@Composable
fun TaiwamfrpTheme(
    theme: String = "System", // Kept for signature compatibility but overrides based on system if M3 dynamic used
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && theme == "System" -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme == "Light" -> FallbackLightColorScheme
        theme == "Dark" -> FallbackDarkColorScheme
        theme == "Blue" -> BlueColorScheme
        theme == "BlueLight" -> BlueLightColorScheme
        theme == "Green" -> GreenColorScheme
        theme == "GreenLight" -> GreenLightColorScheme
        theme == "Oled" -> OledColorScheme
        theme == "White" -> WhiteColorScheme
        darkTheme -> FallbackDarkColorScheme
        else -> FallbackLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = ExpressiveShapes,
        typography = Typography,
        content = content
    )
}

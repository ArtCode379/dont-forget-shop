package shop.dontforgetshop.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DtfgsPrimaryDark,
    onPrimary = DtfgsBackgroundDark,
    primaryContainer = DtfgsSurfaceDark,
    onPrimaryContainer = DtfgsPrimaryDark,
    secondary = DtfgsAccentDark,
    onSecondary = DtfgsBackgroundDark,
    secondaryContainer = Color(0xFF2C2416),
    onSecondaryContainer = DtfgsAccentDark,
    tertiary = DtfgsAccentDark,
    onTertiary = DtfgsBackgroundDark,
    background = DtfgsBackgroundDark,
    onBackground = DtfgsTextDark,
    surface = DtfgsSurfaceDark,
    onSurface = DtfgsTextDark,
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = DtfgsTextLightDark,
    outline = DtfgsBorderDark,
    error = DtfgsError,
)

private val LightColorScheme = lightColorScheme(
    primary = DtfgsPrimary,
    onPrimary = DtfgsSurface,
    primaryContainer = Color(0xFF2A2A2A),
    onPrimaryContainer = DtfgsBackground,
    secondary = DtfgsAccent,
    onSecondary = DtfgsPrimary,
    secondaryContainer = Color(0xFFF5EDD8),
    onSecondaryContainer = Color(0xFF3D2F0A),
    tertiary = DtfgsAccent,
    onTertiary = DtfgsPrimary,
    background = DtfgsBackground,
    onBackground = DtfgsText,
    surface = DtfgsSurface,
    onSurface = DtfgsText,
    surfaceVariant = Color(0xFFF0EDE6),
    onSurfaceVariant = DtfgsTextLight,
    outline = DtfgsBorder,
    error = DtfgsError,
)

@Composable
fun ProductAppDTFGSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

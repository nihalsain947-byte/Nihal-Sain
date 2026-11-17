package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val NihalxDarkColorScheme = darkColorScheme(
    primary = NeonPurple,
    onPrimary = TextPrimary,
    primaryContainer = GlassSurfaceElevated,
    onPrimaryContainer = TextPrimary,
    secondary = ElectricBlue,
    onSecondary = TextPrimary,
    secondaryContainer = GlassSurfaceSolid,
    onSecondaryContainer = TextPrimary,
    tertiary = CyberCyan,
    onTertiary = ObsidianBlack,
    background = ObsidianBlack,
    onBackground = TextPrimary,
    surface = SpaceVoid,
    onSurface = TextPrimary,
    surfaceVariant = GlassSurfaceSolid,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorderLight,
    outlineVariant = GlassBorderAccent,
    error = RoseAccent,
    onError = TextPrimary
)

val NihalxShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun NihalxTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NihalxDarkColorScheme,
        typography = NihalxTypography,
        shapes = NihalxShapes,
        content = content
    )
}

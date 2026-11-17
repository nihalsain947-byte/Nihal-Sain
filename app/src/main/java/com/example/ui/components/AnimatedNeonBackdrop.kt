package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.ObsidianBlack
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedNeonBackdrop(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "backdrop_motion")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orb_phase"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_hero_backdrop),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.22f,
            modifier = Modifier.fillMaxSize()
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Floating Neon Purple Orb
            val purpleCenter = Offset(
                x = w * (0.22f + 0.08f * cos(phase)),
                y = h * (0.18f + 0.06f * sin(phase))
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonPurple.copy(alpha = 0.24f),
                        NeonPurple.copy(alpha = 0.06f),
                        Color.Transparent
                    ),
                    center = purpleCenter,
                    radius = w * 0.65f * pulse
                ),
                radius = w * 0.65f * pulse,
                center = purpleCenter
            )

            // Floating Cyber Cyan Orb
            val cyanCenter = Offset(
                x = w * (0.82f + 0.07f * sin(phase)),
                y = h * (0.48f + 0.08f * cos(phase))
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CyberCyan.copy(alpha = 0.20f),
                        ElectricBlue.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = cyanCenter,
                    radius = w * 0.58f * pulse
                ),
                radius = w * 0.58f * pulse,
                center = cyanCenter
            )

            // Bottom Electric Blue Glow
            val blueCenter = Offset(
                x = w * (0.35f + 0.1f * sin(phase * 0.8f)),
                y = h * 0.88f
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ElectricBlue.copy(alpha = 0.18f),
                        Color.Transparent
                    ),
                    center = blueCenter,
                    radius = w * 0.60f
                ),
                radius = w * 0.60f,
                center = blueCenter
            )

            // Subtle futuristic tech grid lines
            val gridStep = 72f
            val lineColor = Color.White.copy(alpha = 0.03f)
            var x = 0f
            while (x < w) {
                drawLine(
                    color = lineColor,
                    start = Offset(x, 0f),
                    end = Offset(x, h),
                    strokeWidth = 1f
                )
                x += gridStep
            }
            var y = 0f
            while (y < h) {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1f
                )
                y += gridStep
            }
        }

        // Dark vignette for crisp foreground readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ObsidianBlack.copy(alpha = 0.35f),
                            ObsidianBlack.copy(alpha = 0.55f),
                            ObsidianBlack.copy(alpha = 0.88f)
                        )
                    )
                )
        )
    }
}

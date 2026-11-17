package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Reusable full-screen initialization overlay featuring a Compose Canvas
 * animated geometric monogram logo with layered neon-glow rings and particles.
 */
@Composable
fun PremiumLoadingScreen(
    modifier: Modifier = Modifier,
    brandTitle: String = "NIHALX",
    subtitle: String = "NIHAL SAIN • DIGITAL STUDIO",
    statusText: String = "Initializing experience...",
    hintText: String? = "Tap anywhere to enter",
    logoSize: Dp = 188.dp,
    onDismiss: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "premium_loading_screen")

    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.72f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_progress"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("loading_overlay")
            .background(ObsidianBlack),
        contentAlignment = Alignment.Center
    ) {
        AnimatedNeonBackdrop()

        if (onDismiss != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismiss
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 32.dp)
        ) {
            AnimatedNeonLogoCanvas(
                size = logoSize,
                glowIntensity = glowPulse
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = brandTitle,
                modifier = Modifier.testTag("loading_brand_title"),
                style = MaterialTheme.typography.displayMedium.merge(
                    TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(TextPrimary, CyberCyan, NeonPurple)
                        ),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 6.sp
                    )
                ),
                textAlign = TextAlign.Center
            )

            if (subtitle.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = CyberCyan.copy(alpha = 0.75f + 0.25f * glowPulse),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Animated neon progress sweep bar
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.08f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f + 0.8f * shimmerProgress)
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(NeonPurple, ElectricBlue, CyberCyan)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = statusText,
                modifier = Modifier.testTag("loading_status_text"),
                style = MaterialTheme.typography.labelSmall,
                color = CyberCyan,
                textAlign = TextAlign.Center
            )

            if (!hintText.isNullOrBlank() && onDismiss != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = hintText,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Reusable Compose Canvas animated monogram emblem with multi-pass neon glow,
 * 3D orbital rings, hexagonal glass shield, and orbiting energy nodes.
 */
@Composable
fun AnimatedNeonLogoCanvas(
    modifier: Modifier = Modifier,
    size: Dp = 188.dp,
    glowIntensity: Float = 1f
) {
    val density = LocalDensity.current.density
    val infiniteTransition = rememberInfiniteTransition(label = "neon_logo_canvas")

    val primaryOrbit by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "primary_orbit"
    )

    val secondaryOrbit by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "secondary_orbit"
    )

    val tilt3D by infiniteTransition.animateFloat(
        initialValue = -16f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tilt_3d"
    )

    val emblemScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emblem_scale"
    )

    val tracePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "trace_phase"
    )

    Box(
        modifier = modifier
            .size(size)
            .semantics { contentDescription = "Animated NIHALX neon logo" },
        contentAlignment = Alignment.Center
    ) {
        // Ambient radial neon aura + orbiting particles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val radius = this.size.minDimension / 2f

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonPurple.copy(alpha = 0.34f * glowIntensity),
                        ElectricBlue.copy(alpha = 0.16f * glowIntensity),
                        CyberCyan.copy(alpha = 0.08f * glowIntensity),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // Orbiting neon energy particles
            val particleColors = listOf(CyberCyan, NeonPurple, ElectricBlue)
            particleColors.forEachIndexed { index, color ->
                val angleRad = ((primaryOrbit + index * 120f) * PI / 180.0).toFloat()
                val orbitR = radius * 0.86f
                val px = center.x + orbitR * cos(angleRad)
                val py = center.y + orbitR * sin(angleRad)
                drawCircle(
                    color = color.copy(alpha = 0.32f),
                    radius = 12f,
                    center = Offset(px, py)
                )
                drawCircle(
                    color = color,
                    radius = 4.5f,
                    center = Offset(px, py)
                )
            }
        }

        // Outer 3D tilted orbital neon ring
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .graphicsLayer {
                    rotationZ = primaryOrbit
                    rotationX = tilt3D
                    cameraDistance = 12f * density
                }
        ) {
            val arcBrush = Brush.sweepGradient(
                listOf(NeonPurple, ElectricBlue, CyberCyan, Color.Transparent, NeonPurple)
            )
            // Soft outer neon glow pass
            drawArc(
                brush = arcBrush,
                startAngle = 0f,
                sweepAngle = 260f,
                useCenter = false,
                style = Stroke(width = 14f, cap = StrokeCap.Round),
                alpha = 0.25f * glowIntensity
            )
            // Crisp bright core pass
            drawArc(
                brush = arcBrush,
                startAngle = 0f,
                sweepAngle = 260f,
                useCenter = false,
                style = Stroke(width = 5f, cap = StrokeCap.Round)
            )
        }

        // Inner counter-rotating orbital ring
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
                .graphicsLayer {
                    rotationZ = secondaryOrbit
                    rotationY = tilt3D
                    cameraDistance = 12f * density
                }
        ) {
            val innerBrush = Brush.sweepGradient(
                listOf(CyberCyan, NeonPurple, Color.Transparent)
            )
            drawArc(
                brush = innerBrush,
                startAngle = 60f,
                sweepAngle = 210f,
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round),
                alpha = 0.22f * glowIntensity
            )
            drawArc(
                brush = innerBrush,
                startAngle = 60f,
                sweepAngle = 210f,
                useCenter = false,
                style = Stroke(width = 3.5f, cap = StrokeCap.Round)
            )
        }

        // Central hexagonal shield + animated NX geometric monogram
        Canvas(
            modifier = Modifier
                .size(size * 0.54f)
                .graphicsLayer {
                    scaleX = emblemScale
                    scaleY = emblemScale
                }
        ) {
            drawHexagonShield(glowIntensity = glowIntensity)
            drawNeonMonogramNX(
                glowIntensity = glowIntensity,
                tracePhase = tracePhase
            )
        }
    }
}

private fun DrawScope.drawHexagonShield(glowIntensity: Float) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r = size.minDimension * 0.48f

    val hexPath = Path().apply {
        for (i in 0 until 6) {
            val angle = ((60 * i - 90) * PI / 180.0).toFloat()
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }

    // Dark glass fill
    drawPath(
        path = hexPath,
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF15192E),
                Color(0xFF0A0C16)
            ),
            start = Offset.Zero,
            end = Offset(size.width, size.height)
        )
    )

    // Outer neon rim glow
    drawPath(
        path = hexPath,
        brush = Brush.linearGradient(listOf(NeonPurple, ElectricBlue, CyberCyan)),
        style = Stroke(width = 8f, join = StrokeJoin.Round),
        alpha = 0.25f * glowIntensity
    )

    // Crisp rim stroke
    drawPath(
        path = hexPath,
        brush = Brush.linearGradient(listOf(NeonPurple, CyberCyan)),
        style = Stroke(width = 2.5f, join = StrokeJoin.Round)
    )
}

private fun DrawScope.drawNeonMonogramNX(
    glowIntensity: Float,
    tracePhase: Float
) {
    val w = size.width
    val h = size.height

    // Stylized geometric "N" fused with "X" spark
    val nPath = Path().apply {
        moveTo(w * 0.28f, h * 0.68f)
        lineTo(w * 0.28f, h * 0.32f)
        lineTo(w * 0.56f, h * 0.68f)
        lineTo(w * 0.56f, h * 0.32f)
    }

    val xCrossPath = Path().apply {
        moveTo(w * 0.52f, h * 0.32f)
        lineTo(w * 0.74f, h * 0.68f)
        moveTo(w * 0.74f, h * 0.32f)
        lineTo(w * 0.52f, h * 0.68f)
    }

    val nBrush = Brush.linearGradient(
        colors = listOf(TextPrimary, CyberCyan),
        start = Offset(w * 0.25f, h * 0.3f),
        end = Offset(w * 0.6f, h * 0.7f)
    )
    val xBrush = Brush.linearGradient(
        colors = listOf(NeonPurple, ElectricBlue, CyberCyan),
        start = Offset(w * 0.5f, h * 0.3f),
        end = Offset(w * 0.75f, h * 0.7f)
    )

    // Soft multi-pass neon glow under N and X
    drawPath(
        path = nPath,
        color = CyberCyan.copy(alpha = 0.35f * glowIntensity),
        style = Stroke(width = 14f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
    drawPath(
        path = xCrossPath,
        color = NeonPurple.copy(alpha = 0.40f * glowIntensity),
        style = Stroke(width = 14f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )

    // Core crisp geometric strokes
    drawPath(
        path = nPath,
        brush = nBrush,
        style = Stroke(width = 5.5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
    drawPath(
        path = xCrossPath,
        brush = xBrush,
        style = Stroke(width = 5.5f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )

    // Animated traveling neon laser highlight along the N path
    drawPath(
        path = nPath,
        color = Color.White.copy(alpha = 0.85f),
        style = Stroke(
            width = 2.5f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(18f, 82f),
                phase = -tracePhase
            )
        )
    )

    // Glowing corner spark dot
    drawCircle(
        color = CyberCyan.copy(alpha = 0.45f * glowIntensity),
        radius = 8f,
        center = Offset(w * 0.74f, h * 0.32f)
    )
    drawCircle(
        color = TextPrimary,
        radius = 3f,
        center = Offset(w * 0.74f, h * 0.32f)
    )
}

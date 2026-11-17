package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.DesignServices
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.PortfolioData
import com.example.ui.components.GlassCard
import com.example.ui.components.NeonGradientButton
import com.example.ui.components.NeonOutlineButton
import com.example.ui.components.NihalxLogoMark
import com.example.ui.components.SectionHeader
import com.example.ui.components.TechPillTag
import com.example.ui.components.Tilt3DGlassCard
import com.example.ui.navigation.NihalxRoute
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    projectCount: Int,
    onNavigate: (NihalxRoute) -> Unit,
    onOpenInstagram: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val infiniteTransition = rememberInfiniteTransition(label = "home_3d_motion")

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "avatar_float"
    )

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_home"),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Top Brand Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NihalxLogoMark(showSubtitle = true)

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = ObsidianBlack.copy(alpha = 0.7f),
                        border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.45f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldAccent)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "AGRA • OPEN",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            // Hero Welcome & 3D Profile Card
            item {
                Tilt3DGlassCard(
                    accentColor = NeonPurple,
                    cornerRadius = 26.dp,
                    contentPadding = PaddingValues(22.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            TechPillTag(
                                label = "WELCOME TO NIHALX",
                                accentColor = CyberCyan
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Hello, I'm",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextSecondary
                            )
                            Text(
                                text = PortfolioData.FULL_NAME,
                                style = MaterialTheme.typography.displayMedium.merge(
                                    TextStyle(
                                        brush = Brush.linearGradient(
                                            listOf(TextPrimary, CyberCyan, NeonPurple)
                                        ),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // 3D Floating Profile Emblem with Orbital Neon Ring
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .graphicsLayer {
                                    translationY = floatOffset
                                    rotationY = floatOffset * 1.2f
                                    cameraDistance = 12f * density
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer { rotationZ = ringRotation }
                            ) {
                                drawArc(
                                    brush = Brush.sweepGradient(
                                        listOf(NeonPurple, ElectricBlue, CyberCyan, Color.Transparent)
                                    ),
                                    startAngle = 0f,
                                    sweepAngle = 290f,
                                    useCenter = false,
                                    style = Stroke(width = 5f, cap = StrokeCap.Round)
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.img_creator_emblem),
                                contentDescription = stringResource(R.string.cd_creator_avatar),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(1.2.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = PortfolioData.ROLE_HEADLINE,
                        style = MaterialTheme.typography.labelLarge,
                        color = NeonPurple
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = PortfolioData.INTRO_SHORT,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = CyberCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = PortfolioData.LOCATION,
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        NeonGradientButton(
                            text = "Explore Projects",
                            icon = Icons.Rounded.RocketLaunch,
                            onClick = { onNavigate(NihalxRoute.Projects) },
                            testTag = "home_explore_projects_btn",
                            modifier = Modifier.weight(1f)
                        )
                        NeonOutlineButton(
                            text = "Contact Me",
                            icon = Icons.Rounded.AlternateEmail,
                            onClick = { onNavigate(NihalxRoute.Contact) },
                            testTag = "home_contact_btn",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 3D Metric Highlight Strip
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HomeStatTile(
                        value = "08",
                        label = "Core Skills",
                        accent = NeonPurple,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(NihalxRoute.Skills) }
                    )
                    HomeStatTile(
                        value = "05",
                        label = "Services",
                        accent = CyberCyan,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(NihalxRoute.Services) }
                    )
                    HomeStatTile(
                        value = if (projectCount == 0) "NEW" else projectCount.toString().padStart(2, '0'),
                        label = "Projects",
                        accent = ElectricBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(NihalxRoute.Projects) }
                    )
                }
            }

            // Quick Studio Navigation Section
            item {
                SectionHeader(
                    eyebrow = "Portfolio Matrix",
                    title = "Explore NIHALX",
                    subtitle = "Jump directly into my background, skills, projects, and digital services."
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    HomeQuickLinkCard(
                        title = "About Me & Education",
                        subtitle = "Class 11th • ITI Electrician • Tech Vision",
                        icon = Icons.Rounded.Person,
                        accentColor = ElectricBlue,
                        testTag = "quick_card_about",
                        onClick = { onNavigate(NihalxRoute.About) }
                    )
                    HomeQuickLinkCard(
                        title = "Animated Technical Skills",
                        subtitle = "AI Editing, Web Dev, Android Apps & UI/UX",
                        icon = Icons.Rounded.AutoAwesome,
                        accentColor = NeonPurple,
                        testTag = "quick_card_skills",
                        onClick = { onNavigate(NihalxRoute.Skills) }
                    )
                    HomeQuickLinkCard(
                        title = "Services & Digital Solutions",
                        subtitle = "Hire me for Websites, Apps & AI Content",
                        icon = Icons.Rounded.DesignServices,
                        accentColor = CyberCyan,
                        testTag = "quick_card_services",
                        onClick = { onNavigate(NihalxRoute.Services) }
                    )
                }
            }

            // Social Spotlight Banner
            item {
                GlassCard(
                    accentColor = CyberCyan,
                    onClick = onOpenInstagram,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_instagram_banner")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "FOLLOW ON INSTAGRAM",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyberCyan
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = PortfolioData.INSTAGRAM_HANDLE,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                            Text(
                                text = "AI experiments, edits & developer journey",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = stringResource(R.string.action_instagram),
                            tint = CyberCyan
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun HomeStatTile(
    value: String,
    label: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Tilt3DGlassCard(
        modifier = modifier,
        accentColor = accent,
        cornerRadius = 18.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 14.dp),
        onClick = onClick
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = accent
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            maxLines = 1
        )
    }
}

@Composable
private fun HomeQuickLinkCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        accentColor = accentColor,
        contentPadding = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(accentColor.copy(alpha = 0.16f))
                    .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

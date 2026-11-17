package com.example.ui.screens

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
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.EducationItem
import com.example.data.PortfolioData
import com.example.ui.components.GlassCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.TechPillTag
import com.example.ui.components.Tilt3DGlassCard
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AboutScreen(
    onCopyInfo: (label: String, value: String) -> Unit,
    onOpenInstagram: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_about"),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                SectionHeader(
                    eyebrow = "Identity & Story",
                    title = "About Me",
                    subtitle = "Student, technology builder, and digital innovator from Agra."
                )
            }

            // Personal Identity Header Card
            item {
                Tilt3DGlassCard(
                    accentColor = ElectricBlue,
                    cornerRadius = 24.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_creator_emblem),
                            contentDescription = stringResource(R.string.cd_creator_avatar),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .border(
                                    1.5.dp,
                                    Brush.linearGradient(listOf(NeonPurple, CyberCyan)),
                                    RoundedCornerShape(18.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = PortfolioData.FULL_NAME,
                                style = MaterialTheme.typography.headlineMedium,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Founder • ${PortfolioData.BRAND_NAME}",
                                style = MaterialTheme.typography.labelLarge,
                                color = CyberCyan
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.LocationOn,
                                    contentDescription = null,
                                    tint = NeonPurple,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = PortfolioData.LOCATION,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Bio Narrative Card
            item {
                GlassCard(
                    accentColor = NeonPurple,
                    cornerRadius = 22.dp
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(22.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Brush.verticalGradient(listOf(NeonPurple, CyberCyan)))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Introduction & Vision",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = PortfolioData.ABOUT_PARAGRAPH_1,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = PortfolioData.ABOUT_PARAGRAPH_2,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = PortfolioData.ABOUT_PARAGRAPH_3,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TechPillTag(label = "AI Enthusiast", accentColor = NeonPurple)
                        TechPillTag(label = "Web & App Dev", accentColor = CyberCyan)
                        TechPillTag(label = "Agra, IN", accentColor = ElectricBlue)
                    }
                }
            }

            // Education Timeline
            item {
                SectionHeader(
                    eyebrow = "Academic & Technical",
                    title = "My Education"
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PortfolioData.educationList.forEachIndexed { index, edu ->
                        EducationCard(
                            item = edu,
                            icon = if (index == 0) Icons.Rounded.School else Icons.Rounded.Bolt,
                            accentColor = if (index == 0) ElectricBlue else CyberCyan
                        )
                    }
                }
            }

            // Personal Info & Coordinates
            item {
                SectionHeader(
                    eyebrow = "Verified Coordinates",
                    title = "Personal Details",
                    subtitle = "Tap any row to copy or open profile."
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PersonalDetailRow(
                        label = "Brand Name",
                        value = PortfolioData.BRAND_NAME,
                        icon = Icons.Rounded.RocketLaunch,
                        accent = NeonPurple,
                        onClick = { onCopyInfo("Brand", PortfolioData.BRAND_NAME) }
                    )
                    PersonalDetailRow(
                        label = "Location",
                        value = PortfolioData.LOCATION,
                        icon = Icons.Rounded.LocationOn,
                        accent = CyberCyan,
                        onClick = { onCopyInfo("Location", PortfolioData.LOCATION) }
                    )
                    PersonalDetailRow(
                        label = "Phone",
                        value = PortfolioData.PHONE_NUMBER,
                        icon = Icons.Rounded.Phone,
                        accent = ElectricBlue,
                        onClick = { onCopyInfo("Phone", PortfolioData.PHONE_NUMBER) }
                    )
                    PersonalDetailRow(
                        label = "Email",
                        value = PortfolioData.EMAIL,
                        icon = Icons.Rounded.AlternateEmail,
                        accent = NeonPurple,
                        onClick = { onCopyInfo("Email", PortfolioData.EMAIL) }
                    )
                    PersonalDetailRow(
                        label = "Instagram",
                        value = PortfolioData.INSTAGRAM_HANDLE,
                        icon = Icons.Rounded.RocketLaunch,
                        accent = CyberCyan,
                        onClick = onOpenInstagram
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun EducationCard(
    item: EducationItem,
    icon: ImageVector,
    accentColor: Color
) {
    GlassCard(
        accentColor = accentColor,
        cornerRadius = 20.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.16f))
                    .border(1.dp, accentColor.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.subtitle.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = accentColor
                    )
                    TechPillTag(label = item.status, accentColor = accentColor)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun PersonalDetailRow(
    label: String,
    value: String,
    icon: ImageVector,
    accent: Color,
    onClick: () -> Unit
) {
    GlassCard(
        accentColor = accent,
        cornerRadius = 16.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            }
            Icon(
                imageVector = Icons.Rounded.ContentCopy,
                contentDescription = stringResource(R.string.cd_copy_value),
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

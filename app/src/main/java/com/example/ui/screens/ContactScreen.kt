package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.PortfolioData
import com.example.ui.components.GlassCard
import com.example.ui.components.NeonGradientButton
import com.example.ui.components.NeonOutlineButton
import com.example.ui.components.SectionHeader
import com.example.ui.components.Tilt3DGlassCard
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldAccent
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ContactScreen(
    onCallNow: () -> Unit,
    onWhatsApp: (customMessage: String?) -> Unit,
    onEmail: (customMessage: String?) -> Unit,
    onInstagram: () -> Unit,
    onCopy: (label: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var quickNote by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_contact"),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SectionHeader(
                    eyebrow = "Direct Connection",
                    title = "Contact Me",
                    subtitle = "Reach out directly via Call, WhatsApp, Email, or Instagram."
                )
            }

            // Availability & Location Banner
            item {
                Tilt3DGlassCard(
                    accentColor = CyberCyan,
                    cornerRadius = 22.dp
                ) {
                    Text(
                        text = "Let's Build Together",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Have an idea for a website, Android app, or AI visual edit? Connect with ${PortfolioData.FULL_NAME} (${PortfolioData.BRAND_NAME}) directly.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = CyberCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = PortfolioData.LOCATION,
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary
                        )
                    }
                }
            }

            // 4 Primary Interactive Contact Cards
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ContactActionTile(
                        title = stringResource(R.string.action_call_now),
                        subtitle = "+91 ${PortfolioData.PHONE_NUMBER} • Opens Phone Dialer",
                        icon = Icons.Rounded.Call,
                        accentColor = ElectricBlue,
                        testTag = "contact_call_button",
                        onClick = onCallNow,
                        onCopyClick = { onCopy("Phone", PortfolioData.PHONE_NUMBER) }
                    )

                    ContactActionTile(
                        title = stringResource(R.string.action_whatsapp),
                        subtitle = "+91 ${PortfolioData.PHONE_NUMBER} • Instant Chat",
                        icon = Icons.AutoMirrored.Rounded.Chat,
                        accentColor = EmeraldAccent,
                        testTag = "contact_whatsapp_button",
                        onClick = { onWhatsApp(null) },
                        onCopyClick = { onCopy("WhatsApp", PortfolioData.PHONE_NUMBER) }
                    )

                    ContactActionTile(
                        title = stringResource(R.string.action_email),
                        subtitle = PortfolioData.EMAIL,
                        icon = Icons.Rounded.AlternateEmail,
                        accentColor = NeonPurple,
                        testTag = "contact_email_button",
                        onClick = { onEmail(null) },
                        onCopyClick = { onCopy("Email", PortfolioData.EMAIL) }
                    )

                    ContactActionTile(
                        title = stringResource(R.string.action_instagram),
                        subtitle = "${PortfolioData.INSTAGRAM_HANDLE} • View Profile",
                        icon = Icons.Rounded.CameraAlt,
                        accentColor = CyberCyan,
                        testTag = "contact_instagram_button",
                        onClick = onInstagram,
                        onCopyClick = { onCopy("Instagram", PortfolioData.INSTAGRAM_HANDLE) }
                    )
                }
            }

            // Quick Message Composer
            item {
                GlassCard(
                    accentColor = NeonPurple,
                    cornerRadius = 22.dp
                ) {
                    Text(
                        text = "Quick Inquiry Composer",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Write a brief message and launch WhatsApp or Email pre-filled.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = quickNote,
                        onValueChange = { quickNote = it },
                        label = { Text("Your project idea or question") },
                        minLines = 3,
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberCyan,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                            focusedLabelColor = CyberCyan,
                            unfocusedLabelColor = TextSecondary,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = CyberCyan
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_quick_note_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NeonGradientButton(
                            text = "Send WhatsApp",
                            icon = Icons.AutoMirrored.Rounded.Chat,
                            onClick = { onWhatsApp(quickNote.takeIf { it.isNotBlank() }) },
                            testTag = "contact_send_whatsapp",
                            modifier = Modifier.weight(1f)
                        )
                        NeonOutlineButton(
                            text = "Send Email",
                            icon = Icons.Rounded.AlternateEmail,
                            accentColor = NeonPurple,
                            onClick = { onEmail(quickNote.takeIf { it.isNotBlank() }) },
                            testTag = "contact_send_email",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun ContactActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    onClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    Tilt3DGlassCard(
        accentColor = accentColor,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                accentColor.copy(alpha = 0.28f),
                                accentColor.copy(alpha = 0.08f)
                            )
                        )
                    )
                    .border(1.dp, accentColor.copy(alpha = 0.55f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            IconButton(onClick = onCopyClick) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = stringResource(R.string.cd_copy_value),
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

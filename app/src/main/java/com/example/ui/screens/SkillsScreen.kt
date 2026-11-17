package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.SkillCategory
import com.example.data.SkillItem
import com.example.ui.components.SectionHeader
import com.example.ui.components.TechPillTag
import com.example.ui.components.Tilt3DGlassCard
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.GlassSurfaceSolid
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun SkillsScreen(
    skills: List<SkillItem>,
    selectedCategory: SkillCategory,
    expandedSkillId: String?,
    onSelectCategory: (SkillCategory) -> Unit,
    onToggleSkill: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_skills"),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                SectionHeader(
                    eyebrow = "Capabilities & Craft",
                    title = "My Skills",
                    subtitle = "Tap any 3D skill card to inspect tools and technical focus."
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SkillCategory.entries.forEach { category ->
                        val selected = category == selectedCategory
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectCategory(category) },
                            label = {
                                Text(
                                    text = category.label,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            modifier = Modifier.testTag("skill_filter_${category.name.lowercase()}"),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = GlassSurfaceSolid,
                                labelColor = TextSecondary,
                                selectedContainerColor = NeonPurple.copy(alpha = 0.25f),
                                selectedLabelColor = TextPrimary
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (selected) CyberCyan else Color.White.copy(alpha = 0.14f)
                            )
                        )
                    }
                }
            }

            itemsIndexed(
                items = skills,
                key = { _, item -> item.id }
            ) { index, skill ->
                AnimatedSkillCard(
                    skill = skill,
                    index = index,
                    isExpanded = expandedSkillId == skill.id,
                    onClick = { onToggleSkill(skill.id) }
                )
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimatedSkillCard(
    skill: SkillItem,
    index: Int,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val density = LocalDensity.current.density
    var appeared by remember(skill.id) { mutableStateOf(false) }

    LaunchedEffect(skill.id) {
        delay((index * 55L).coerceAtMost(350L))
        appeared = true
    }

    val entranceOffset by animateFloatAsState(
        targetValue = if (appeared) 0f else 28f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = Spring.StiffnessMediumLow),
        label = "skill_entrance"
    )
    val entranceAlpha by animateFloatAsState(
        targetValue = if (appeared) 1f else 0f,
        animationSpec = tween(durationMillis = 280),
        label = "skill_alpha"
    )
    val progressAnim by animateFloatAsState(
        targetValue = if (appeared) skill.focusLevel else 0f,
        animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing),
        label = "skill_progress"
    )

    Tilt3DGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("skill_card_${skill.id}")
            .graphicsLayer {
                translationY = entranceOffset
                alpha = entranceAlpha
                cameraDistance = 12f * density
            },
        accentColor = skill.accentColor,
        cornerRadius = 20.dp,
        contentPadding = PaddingValues(18.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                skill.accentColor.copy(alpha = 0.28f),
                                skill.accentColor.copy(alpha = 0.08f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = skill.accentColor.copy(alpha = 0.55f),
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = skill.icon,
                    contentDescription = null,
                    tint = skill.accentColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = skill.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = skill.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            Icon(
                imageVector = if (isExpanded) {
                    Icons.Rounded.KeyboardArrowUp
                } else {
                    Icons.Rounded.KeyboardArrowDown
                },
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = skill.accentColor
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Animated neon energy bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.08f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressAnim)
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                listOf(skill.accentColor, CyberCyan)
                            )
                        )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${(skill.focusLevel * 100).toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = skill.accentColor
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(tween(200)) + expandVertically(),
            exit = fadeOut(tween(150)) + shrinkVertically()
        ) {
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Text(
                    text = skill.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skill.tools.forEach { tool ->
                        TechPillTag(label = tool, accentColor = skill.accentColor)
                    }
                }
            }
        }
    }
}

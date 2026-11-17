package com.example.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Brush
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.MovieCreation
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material.icons.rounded.TipsAndUpdates
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonPurple

enum class SkillCategory(val label: String) {
    ALL("All"),
    AI_CREATIVE("AI & Media"),
    DEVELOPMENT("Development"),
    TECH_STRATEGY("Tech & Strategy")
}

data class SkillItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val category: SkillCategory,
    val tools: List<String>,
    val icon: ImageVector,
    val accentColor: Color,
    val focusLevel: Float
)

data class ServiceItem(
    val id: String,
    val title: String,
    val tagline: String,
    val description: String,
    val deliverables: List<String>,
    val icon: ImageVector,
    val accentColor: Color
)

data class EducationItem(
    val title: String,
    val subtitle: String,
    val status: String,
    val details: String
)

object PortfolioData {
    const val BRAND_NAME = "NIHALX"
    const val FULL_NAME = "Nihal Sain"
    const val ROLE_HEADLINE = "Student • Web & App Developer • AI Enthusiast"
    const val LOCATION = "Agra, Uttar Pradesh, India"
    const val PHONE_NUMBER = "9368276066"
    const val WHATSAPP_E164 = "919368276066"
    const val EMAIL = "nihalsain918@gmail.com"
    const val INSTAGRAM_HANDLE = "@Iamnihalsain"
    const val INSTAGRAM_URL = "https://www.instagram.com/iamnihalsain/"

    const val WELCOME_MESSAGE = "Welcome to NIHALX Digital Studio"
    const val INTRO_SHORT =
        "Hello! I am Nihal Sain — a student, website developer, Android app creator, and AI technology enthusiast from Agra, India, crafting futuristic digital products."

    const val ABOUT_PARAGRAPH_1 =
        "Hello! I am Nihal Sain, a student, website developer, app developer, and AI technology enthusiast from Agra, India."
    const val ABOUT_PARAGRAPH_2 =
        "I am currently studying in Class 11th and pursuing an ITI Electrician course. Combining strong electrical & technical fundamentals with modern software engineering lets me think across both hardware and digital systems."
    const val ABOUT_PARAGRAPH_3 =
        "I am passionate about Artificial Intelligence, modern technology, website development, Android app development, and digital innovation. My goal is to build innovative digital products and become a professional technology developer."

    val educationList = listOf(
        EducationItem(
            title = "Class 11th — Senior Secondary",
            subtitle = "Academic Foundation",
            status = "In Progress",
            details = "Building core analytical, mathematical, and science foundations alongside self-driven software development."
        ),
        EducationItem(
            title = "ITI Electrician Course",
            subtitle = "Industrial Technical Institute",
            status = "Pursuing",
            details = "Hands-on technical training in electrical circuits, power systems, safety diagnostics, and hardware engineering."
        )
    )

    val skills = listOf(
        SkillItem(
            id = "ai_photo",
            title = "AI Photo Editing",
            subtitle = "Generative Retouch & Visual Enhancement",
            description = "Transforming portraits and product visuals using modern AI restoration, lighting synthesis, background engineering, and high-resolution enhancement.",
            category = SkillCategory.AI_CREATIVE,
            tools = listOf("AI Upscaling", "Generative Fill", "Color Grading", "Visual Cleanup"),
            icon = Icons.Rounded.AutoFixHigh,
            accentColor = NeonPurple,
            focusLevel = 0.92f
        ),
        SkillItem(
            id = "ai_video",
            title = "AI Video Creation",
            subtitle = "Next-Gen Motion & Reel Production",
            description = "Creating engaging short-form videos, AI-assisted motion storytelling, smart cuts, and futuristic visual edits for creators and brands.",
            category = SkillCategory.AI_CREATIVE,
            tools = listOf("AI Motion", "Smart Cuts", "Visual FX", "Social Reels"),
            icon = Icons.Rounded.MovieCreation,
            accentColor = CyberCyan,
            focusLevel = 0.88f
        ),
        SkillItem(
            id = "ai_tools",
            title = "Artificial Intelligence Tools",
            subtitle = "Prompt Engineering & Automation",
            description = "Leveraging cutting-edge AI models and productivity workflows to accelerate coding, ideation, content creation, and research.",
            category = SkillCategory.AI_CREATIVE,
            tools = listOf("Prompt Systems", "AI Workflows", "Creative Suites", "Automation"),
            icon = Icons.Rounded.Psychology,
            accentColor = ElectricBlue,
            focusLevel = 0.94f
        ),
        SkillItem(
            id = "web_dev",
            title = "Website Development",
            subtitle = "Modern Responsive Web Experiences",
            description = "Designing and building fast, responsive, glassmorphic websites and landing pages optimized for mobile and desktop browsers.",
            category = SkillCategory.DEVELOPMENT,
            tools = listOf("HTML5 / CSS3", "JavaScript", "Responsive UI", "Modern Layouts"),
            icon = Icons.Rounded.Language,
            accentColor = CyberCyan,
            focusLevel = 0.90f
        ),
        SkillItem(
            id = "android_dev",
            title = "Android App Development",
            subtitle = "Native Kotlin & Jetpack Compose",
            description = "Developing functional, smooth Android applications with Material Design 3, clean architecture, local persistence, and real device integrations.",
            category = SkillCategory.DEVELOPMENT,
            tools = listOf("Kotlin", "Jetpack Compose", "Material 3", "Room & Intents"),
            icon = Icons.Rounded.Android,
            accentColor = NeonPurple,
            focusLevel = 0.89f
        ),
        SkillItem(
            id = "ui_ux",
            title = "UI/UX Design",
            subtitle = "Dark Luxury & Intuitive Interfaces",
            description = "Crafting clean user journeys, interactive micro-animations, glassmorphic cards, and high-contrast typography systems.",
            category = SkillCategory.DEVELOPMENT,
            tools = listOf("Design Systems", "Wireframing", "Dark Themes", "Motion UX"),
            icon = Icons.Rounded.Palette,
            accentColor = ElectricBlue,
            focusLevel = 0.87f
        ),
        SkillItem(
            id = "tech_experiments",
            title = "Technical Experiments",
            subtitle = "Hardware + Software Prototyping",
            description = "Exploring practical electronics from ITI training alongside software tools, testing new frameworks, and prototyping clever tech ideas.",
            category = SkillCategory.TECH_STRATEGY,
            tools = listOf("Electrical Basics", "Rapid Prototyping", "Testing", "R&D"),
            icon = Icons.Rounded.Science,
            accentColor = CyberCyan,
            focusLevel = 0.85f
        ),
        SkillItem(
            id = "digital_solutions",
            title = "Digital Solutions",
            subtitle = "End-to-End Creator & Business Tech",
            description = "Combining web presence, mobile apps, and AI media into practical digital packages that help individuals and local businesses grow online.",
            category = SkillCategory.TECH_STRATEGY,
            tools = listOf("Brand Presence", "Tech Consulting", "Content Tech", "Launch Support"),
            icon = Icons.Rounded.TipsAndUpdates,
            accentColor = NeonPurple,
            focusLevel = 0.91f
        )
    )

    val services = listOf(
        ServiceItem(
            id = "srv_web",
            title = "Website Development",
            tagline = "Sleek, fast & mobile-ready websites",
            description = "Custom portfolio websites, personal brand pages, and business landing pages built with modern aesthetics and responsive layouts.",
            deliverables = listOf(
                "Responsive mobile & desktop design",
                "Modern dark/light glassmorphic UI",
                "Direct WhatsApp & social integration"
            ),
            icon = Icons.Rounded.Language,
            accentColor = CyberCyan
        ),
        ServiceItem(
            id = "srv_android",
            title = "Android App Development",
            tagline = "Native Kotlin & Jetpack Compose apps",
            description = "Custom Android apps tailored for portfolios, tools, local businesses, and creators — engineered cleanly for smooth phone performance.",
            deliverables = listOf(
                "Jetpack Compose Material 3 UI",
                "Smooth animations & offline persistence",
                "Installable APK build & testing ready"
            ),
            icon = Icons.Rounded.Android,
            accentColor = NeonPurple
        ),
        ServiceItem(
            id = "srv_ai_photo",
            title = "AI Photo Editing",
            tagline = "Studio-grade AI visual enhancement",
            description = "High-impact photo retouching, background replacement, creative portraits, and social media thumbnails powered by AI precision.",
            deliverables = listOf(
                "Portrait enhancement & color grading",
                "Creative composite & cyber styling",
                "Crisp high-resolution export"
            ),
            icon = Icons.Rounded.Brush,
            accentColor = ElectricBlue
        ),
        ServiceItem(
            id = "srv_ai_video",
            title = "AI Video Editing",
            tagline = "Dynamic AI reels & motion visuals",
            description = "Attention-grabbing short-form reels, promo edits, transitions, and AI-enhanced visual storytelling crafted for Instagram and YouTube.",
            deliverables = listOf(
                "AI-assisted visual effects & pacing",
                "Captions, sound sync & color polish",
                "Vertical 9:16 & widescreen formats"
            ),
            icon = Icons.Rounded.AutoAwesome,
            accentColor = CyberCyan
        ),
        ServiceItem(
            id = "srv_digital",
            title = "Digital Solutions",
            tagline = "Complete digital growth & tech setup",
            description = "Practical end-to-end digital support combining websites, mobile apps, AI creative assets, and smart online presence setup.",
            deliverables = listOf(
                "Unified brand & digital identity",
                "AI workflow setup for productivity",
                "Ongoing technical guidance & updates"
            ),
            icon = Icons.Rounded.Hub,
            accentColor = NeonPurple
        )
    )
}

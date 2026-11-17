package com.example.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.DesignServices
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.R

enum class NihalxRoute(
    val route: String,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    Home(
        route = "home",
        labelRes = R.string.nav_home,
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    About(
        route = "about",
        labelRes = R.string.nav_about,
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.Person
    ),
    Skills(
        route = "skills",
        labelRes = R.string.nav_skills,
        selectedIcon = Icons.Rounded.AutoAwesome,
        unselectedIcon = Icons.Outlined.AutoAwesome
    ),
    Projects(
        route = "projects",
        labelRes = R.string.nav_projects,
        selectedIcon = Icons.Rounded.RocketLaunch,
        unselectedIcon = Icons.Outlined.RocketLaunch
    ),
    Services(
        route = "services",
        labelRes = R.string.nav_services,
        selectedIcon = Icons.Rounded.DesignServices,
        unselectedIcon = Icons.Outlined.DesignServices
    ),
    Contact(
        route = "contact",
        labelRes = R.string.nav_contact,
        selectedIcon = Icons.Rounded.AlternateEmail,
        unselectedIcon = Icons.Outlined.AlternateEmail
    )
}

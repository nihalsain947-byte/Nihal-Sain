package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.AnimatedNeonBackdrop
import com.example.ui.components.PremiumLoadingScreen
import com.example.ui.navigation.NihalxRoute
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.screens.SkillsScreen
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.GlassSurfaceElevated
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SpaceVoid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.ActionResult
import com.example.util.ContactActions
import kotlinx.coroutines.launch

@Composable
fun NihalxApp(
    viewModel: PortfolioViewModel = viewModel()
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isIntroLoading by viewModel.isIntroLoading.collectAsStateWithLifecycle()
    val skills by viewModel.filteredSkills.collectAsStateWithLifecycle()
    val selectedSkillCategory by viewModel.selectedSkillCategory.collectAsStateWithLifecycle()
    val expandedSkillId by viewModel.expandedSkillId.collectAsStateWithLifecycle()
    val projectsUiState by viewModel.projectsUiState.collectAsStateWithLifecycle()
    val selectedProjectFilter by viewModel.selectedProjectFilter.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NihalxRoute.Home.route

    val projectCount = when (val state = projectsUiState) {
        is ProjectsUiState.Success -> state.projects.size
        else -> 0
    }

    fun handleActionResult(result: ActionResult) {
        if (result is ActionResult.Feedback) {
            scope.launch {
                snackbarHostState.showSnackbar(result.message)
            }
        }
    }

    fun showMessage(msg: String) {
        scope.launch {
            snackbarHostState.showSnackbar(msg)
        }
    }

    fun navigateTo(route: NihalxRoute) {
        navController.navigate(route.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedNeonBackdrop()

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val useNavigationRail = maxWidth >= 600.dp

            Scaffold(
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets.safeDrawing,
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = GlassSurfaceElevated,
                            contentColor = TextPrimary,
                            actionColor = CyberCyan,
                            shape = RoundedCornerShape(14.dp)
                        )
                    }
                },
                bottomBar = {
                    if (!useNavigationRail) {
                        NihalxBottomNavigationBar(
                            currentRoute = currentRoute,
                            onNavigate = ::navigateTo
                        )
                    }
                }
            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (useNavigationRail) {
                        NihalxNavigationRail(
                            currentRoute = currentRoute,
                            onNavigate = ::navigateTo
                        )
                    }

                    NihalxNavHost(
                        navController = navController,
                        projectCount = projectCount,
                        skills = skills,
                        selectedSkillCategory = selectedSkillCategory,
                        expandedSkillId = expandedSkillId,
                        projectsUiState = projectsUiState,
                        selectedProjectFilter = selectedProjectFilter,
                        onNavigate = ::navigateTo,
                        onSelectSkillCategory = viewModel::selectSkillCategory,
                        onToggleSkill = viewModel::toggleSkillExpanded,
                        onSelectProjectFilter = viewModel::selectProjectFilter,
                        onSaveProject = { existingId, title, category, desc, stack, url, uri, oldPath ->
                            viewModel.saveProject(
                                existingId = existingId,
                                title = title,
                                category = category,
                                description = desc,
                                techStack = stack,
                                projectUrl = url,
                                pickedImageUri = uri,
                                existingImagePath = oldPath
                            ) { _, message ->
                                showMessage(message)
                            }
                        },
                        onDeleteProject = { project ->
                            viewModel.deleteProject(project) { message ->
                                showMessage(message)
                            }
                        },
                        onOpenUrl = { url ->
                            handleActionResult(ContactActions.openWebUrl(context, url))
                        },
                        onCallNow = {
                            handleActionResult(ContactActions.openDialer(context))
                        },
                        onWhatsApp = { customMessage ->
                            val msg = customMessage
                                ?: "Hello Nihal! I visited your NIHALX portfolio app and would like to connect."
                            handleActionResult(
                                ContactActions.openWhatsApp(context, prefilledMessage = msg)
                            )
                        },
                        onEmail = { customMessage ->
                            val body = customMessage
                                ?: "Hello Nihal Sain,\n\nI explored your NIHALX portfolio app and would love to discuss a project."
                            handleActionResult(
                                ContactActions.openEmail(context, body = body)
                            )
                        },
                        onInstagram = {
                            handleActionResult(ContactActions.openInstagram(context))
                        },
                        onCopyInfo = { label, value ->
                            val feedback = ContactActions.copyToClipboard(context, label, value)
                            showMessage(feedback)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = isIntroLoading,
            enter = fadeIn(tween(150)),
            exit = fadeOut(tween(420))
        ) {
            PremiumLoadingScreen(
                brandTitle = "NIHALX",
                subtitle = "NIHAL SAIN • DIGITAL STUDIO",
                statusText = "Initializing portfolio...",
                hintText = "Tap anywhere to enter",
                onDismiss = viewModel::skipIntro
            )
        }
    }
}

@Composable
private fun NihalxNavHost(
    navController: NavHostController,
    projectCount: Int,
    skills: List<com.example.data.SkillItem>,
    selectedSkillCategory: com.example.data.SkillCategory,
    expandedSkillId: String?,
    projectsUiState: ProjectsUiState,
    selectedProjectFilter: ProjectFilter,
    onNavigate: (NihalxRoute) -> Unit,
    onSelectSkillCategory: (com.example.data.SkillCategory) -> Unit,
    onToggleSkill: (String) -> Unit,
    onSelectProjectFilter: (ProjectFilter) -> Unit,
    onSaveProject: (
        Int?,
        String,
        com.example.data.local.ProjectCategory,
        String,
        String,
        String,
        android.net.Uri?,
        String
    ) -> Unit,
    onDeleteProject: (com.example.data.local.ProjectEntity) -> Unit,
    onOpenUrl: (String) -> Unit,
    onCallNow: () -> Unit,
    onWhatsApp: (String?) -> Unit,
    onEmail: (String?) -> Unit,
    onInstagram: () -> Unit,
    onCopyInfo: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NihalxRoute.Home.route,
        modifier = modifier.fillMaxSize(),
        enterTransition = {
            fadeIn(tween(240)) + slideInHorizontally(tween(240)) { it / 16 }
        },
        exitTransition = {
            fadeOut(tween(200)) + slideOutHorizontally(tween(200)) { -it / 16 }
        },
        popEnterTransition = {
            fadeIn(tween(240)) + slideInHorizontally(tween(240)) { -it / 16 }
        },
        popExitTransition = {
            fadeOut(tween(200)) + slideOutHorizontally(tween(200)) { it / 16 }
        }
    ) {
        composable(NihalxRoute.Home.route) {
            HomeScreen(
                projectCount = projectCount,
                onNavigate = onNavigate,
                onOpenInstagram = onInstagram
            )
        }
        composable(NihalxRoute.About.route) {
            AboutScreen(
                onCopyInfo = onCopyInfo,
                onOpenInstagram = onInstagram
            )
        }
        composable(NihalxRoute.Skills.route) {
            SkillsScreen(
                skills = skills,
                selectedCategory = selectedSkillCategory,
                expandedSkillId = expandedSkillId,
                onSelectCategory = onSelectSkillCategory,
                onToggleSkill = onToggleSkill
            )
        }
        composable(NihalxRoute.Projects.route) {
            ProjectsScreen(
                uiState = projectsUiState,
                selectedFilter = selectedProjectFilter,
                onSelectFilter = onSelectProjectFilter,
                onSaveProject = onSaveProject,
                onDeleteProject = onDeleteProject,
                onOpenUrl = onOpenUrl
            )
        }
        composable(NihalxRoute.Services.route) {
            ServicesScreen(
                onInquireWhatsApp = { serviceTitle ->
                    onWhatsApp("Hello Nihal! I'm interested in your $serviceTitle service from the NIHALX app.")
                },
                onInquireEmail = { serviceTitle ->
                    onEmail("Hello Nihal Sain,\n\nI would like to inquire about your $serviceTitle service.")
                }
            )
        }
        composable(NihalxRoute.Contact.route) {
            ContactScreen(
                onCallNow = onCallNow,
                onWhatsApp = onWhatsApp,
                onEmail = onEmail,
                onInstagram = onInstagram,
                onCopy = onCopyInfo
            )
        }
    }
}

@Composable
private fun NihalxBottomNavigationBar(
    currentRoute: String,
    onNavigate: (NihalxRoute) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        SpaceVoid.copy(alpha = 0.92f),
                        ObsidianBlack.copy(alpha = 0.98f)
                    )
                )
            )
            .border(
                width = 0.8.dp,
                color = Color.White.copy(alpha = 0.1f)
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            NihalxRoute.entries.forEach { destination ->
                val selected = currentRoute == destination.route
                val label = stringResource(destination.labelRes)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(destination) },
                    modifier = Modifier.testTag("nav_item_${destination.route}"),
                    icon = {
                        Icon(
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = label,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberCyan,
                        selectedTextColor = CyberCyan,
                        indicatorColor = NeonPurple.copy(alpha = 0.24f),
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        }
    }
}

@Composable
private fun NihalxNavigationRail(
    currentRoute: String,
    onNavigate: (NihalxRoute) -> Unit
) {
    NavigationRail(
        containerColor = SpaceVoid.copy(alpha = 0.88f),
        modifier = Modifier
            .fillMaxHeight()
            .border(0.8.dp, Color.White.copy(alpha = 0.1f))
    ) {
        NihalxRoute.entries.forEach { destination ->
            val selected = currentRoute == destination.route
            val label = stringResource(destination.labelRes)
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigate(destination) },
                modifier = Modifier.testTag("rail_item_${destination.route}"),
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        maxLines = 1
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = CyberCyan,
                    selectedTextColor = CyberCyan,
                    indicatorColor = NeonPurple.copy(alpha = 0.24f),
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}

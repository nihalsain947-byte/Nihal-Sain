package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.local.ProjectCategory
import com.example.data.local.ProjectEntity
import com.example.ui.ProjectFilter
import com.example.ui.ProjectsUiState
import com.example.ui.components.GlassCard
import com.example.ui.components.NeonGradientButton
import com.example.ui.components.NeonOutlineButton
import com.example.ui.components.SectionHeader
import com.example.ui.components.TechPillTag
import com.example.ui.components.Tilt3DGlassCard
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSurfaceElevated
import com.example.ui.theme.GlassSurfaceSolid
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.SpaceVoid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    uiState: ProjectsUiState,
    selectedFilter: ProjectFilter,
    onSelectFilter: (ProjectFilter) -> Unit,
    onSaveProject: (
        existingId: Int?,
        title: String,
        category: ProjectCategory,
        description: String,
        techStack: String,
        projectUrl: String,
        pickedImageUri: Uri?,
        existingImagePath: String
    ) -> Unit,
    onDeleteProject: (ProjectEntity) -> Unit,
    onOpenUrl: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }
    var editingProject by remember { mutableStateOf<ProjectEntity?>(null) }
    var projectToDelete by remember { mutableStateOf<ProjectEntity?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("screen_projects"),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 18.dp,
                bottom = 96.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SectionHeader(
                    eyebrow = "Real Work Showcase",
                    title = "My Projects",
                    subtitle = "Add and manage your real Website, Android App, and AI Editing builds."
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ProjectFilter.entries.forEach { filter ->
                        val selected = filter == selectedFilter
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectFilter(filter) },
                            label = {
                                Text(
                                    text = filter.label,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            modifier = Modifier.testTag("project_filter_${filter.name.lowercase()}"),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = GlassSurfaceSolid,
                                labelColor = TextSecondary,
                                selectedContainerColor = CyberCyan.copy(alpha = 0.22f),
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

            when (uiState) {
                is ProjectsUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = CyberCyan)
                        }
                    }
                }

                is ProjectsUiState.Error -> {
                    item {
                        GlassCard(accentColor = RoseAccent) {
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = RoseAccent
                            )
                        }
                    }
                }

                is ProjectsUiState.Empty -> {
                    item {
                        ProjectsEmptyStateCard(
                            onAddClick = {
                                editingProject = null
                                showSheet = true
                            }
                        )
                    }
                }

                is ProjectsUiState.Success -> {
                    if (uiState.projects.isEmpty()) {
                        item {
                            GlassCard(accentColor = ElectricBlue) {
                                Text(
                                    text = "No projects in \"${selectedFilter.label}\" yet.",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Tap the + button below to add a real ${selectedFilter.label} project.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    } else {
                        items(
                            items = uiState.projects,
                            key = { it.id }
                        ) { project ->
                            ProjectItemCard(
                                project = project,
                                onEdit = {
                                    editingProject = project
                                    showSheet = true
                                },
                                onDelete = { projectToDelete = project },
                                onOpenUrl = onOpenUrl
                            )
                        }
                    }
                }
            }
        }

        // Floating Action Button to add real project anytime
        FloatingActionButton(
            onClick = {
                editingProject = null
                showSheet = true
            },
            containerColor = NeonPurple,
            contentColor = TextPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("fab_add_project")
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.cd_add_project)
            )
        }
    }

    if (showSheet) {
        AddEditProjectBottomSheet(
            existing = editingProject,
            onDismiss = { showSheet = false },
            onSave = { title, category, description, techStack, url, pickedUri ->
                onSaveProject(
                    editingProject?.id,
                    title,
                    category,
                    description,
                    techStack,
                    url,
                    pickedUri,
                    editingProject?.imagePath.orEmpty()
                )
                showSheet = false
            }
        )
    }

    projectToDelete?.let { target ->
        AlertDialog(
            onDismissRequest = { projectToDelete = null },
            containerColor = GlassSurfaceElevated,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary,
            title = { Text("Delete Project?") },
            text = { Text("Remove \"${target.title}\" from your NIHALX portfolio?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteProject(target)
                        projectToDelete = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = RoseAccent)
                ) {
                    Text(stringResource(R.string.action_delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { projectToDelete = null }) {
                    Text(stringResource(R.string.action_cancel), color = TextSecondary)
                }
            }
        )
    }
}

@Composable
private fun ProjectsEmptyStateCard(
    onAddClick: () -> Unit
) {
    Tilt3DGlassCard(
        accentColor = CyberCyan,
        cornerRadius = 24.dp,
        contentPadding = PaddingValues(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("projects_empty_card")
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_projects_blueprint),
            contentDescription = stringResource(R.string.cd_projects_empty),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(175.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, CyberCyan.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TechPillTag(
            label = "AUTHENTIC PORTFOLIO",
            accentColor = NeonPurple
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Ready for Your Real Projects",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "No placeholder or fake projects are shown here. Add your own real Website, Android App, and AI Photo/Video Editing projects with screenshots, descriptions, and links — saved directly on your device.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(18.dp))

        NeonGradientButton(
            text = stringResource(R.string.action_add_project),
            icon = Icons.Rounded.Add,
            onClick = onAddClick,
            testTag = "empty_add_project_btn",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectItemCard(
    project: ProjectEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onOpenUrl: (String) -> Unit
) {
    val category = ProjectCategory.entries.find { it.name == project.category } ?: ProjectCategory.WEBSITE
    val accent = when (category) {
        ProjectCategory.WEBSITE -> CyberCyan
        ProjectCategory.ANDROID -> NeonPurple
        ProjectCategory.AI_EDITING -> ElectricBlue
    }
    val categoryIcon = when (category) {
        ProjectCategory.WEBSITE -> Icons.Rounded.Language
        ProjectCategory.ANDROID -> Icons.Rounded.Android
        ProjectCategory.AI_EDITING -> Icons.Rounded.AutoFixHigh
    }

    Tilt3DGlassCard(
        accentColor = accent,
        cornerRadius = 22.dp,
        contentPadding = PaddingValues(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("project_card_${project.id}")
    ) {
        if (project.imagePath.isNotBlank() && File(project.imagePath).exists()) {
            AsyncImage(
                model = File(project.imagePath),
                contentDescription = project.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, accent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(14.dp))
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                accent.copy(alpha = 0.25f),
                                ObsidianBlack.copy(alpha = 0.8f),
                                NeonPurple.copy(alpha = 0.18f)
                            )
                        )
                    )
                    .border(1.dp, accent.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIcon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TechPillTag(label = category.displayName.uppercase(), accentColor = accent)
            Row {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.testTag("edit_project_${project.id}")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(R.string.action_edit),
                        tint = CyberCyan
                    )
                }
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_project_${project.id}")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DeleteOutline,
                        contentDescription = stringResource(R.string.action_delete),
                        tint = RoseAccent
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = project.title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = project.description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        val tags = project.techStack.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        if (tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    TechPillTag(label = tag, accentColor = accent)
                }
            }
        }

        if (project.projectUrl.isNotBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            NeonOutlineButton(
                text = stringResource(R.string.action_open_link),
                icon = Icons.AutoMirrored.Rounded.OpenInNew,
                accentColor = accent,
                onClick = { onOpenUrl(project.projectUrl) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEditProjectBottomSheet(
    existing: ProjectEntity?,
    onDismiss: () -> Unit,
    onSave: (
        title: String,
        category: ProjectCategory,
        description: String,
        techStack: String,
        projectUrl: String,
        pickedImageUri: Uri?
    ) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember(existing) { mutableStateOf(existing?.title.orEmpty()) }
    var category by remember(existing) {
        mutableStateOf(
            ProjectCategory.entries.find { it.name == existing?.category } ?: ProjectCategory.WEBSITE
        )
    }
    var description by remember(existing) { mutableStateOf(existing?.description.orEmpty()) }
    var techStack by remember(existing) { mutableStateOf(existing?.techStack.orEmpty()) }
    var projectUrl by remember(existing) { mutableStateOf(existing?.projectUrl.orEmpty()) }
    var pickedImageUri by remember { mutableStateOf<Uri?>(null) }
    var validationError by remember { mutableStateOf<String?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            pickedImageUri = uri
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SpaceVoid,
        contentColor = TextPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = if (existing == null) "Add Real Project" else "Edit Project",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )

            // Zero-permission Photo Picker Box
            val previewModel: Any? = pickedImageUri
                ?: existing?.imagePath?.takeIf { it.isNotBlank() && File(it).exists() }?.let { File(it) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(GlassSurfaceSolid)
                    .border(1.dp, CyberCyan.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .testTag("pick_project_image_box"),
                contentAlignment = Alignment.Center
            ) {
                if (previewModel != null) {
                    AsyncImage(
                        model = previewModel,
                        contentDescription = stringResource(R.string.cd_pick_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Rounded.AddPhotoAlternate,
                            contentDescription = stringResource(R.string.cd_pick_image),
                            tint = CyberCyan,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Tap to attach project image (Optional)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Category selector
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProjectCategory.entries.forEach { cat ->
                    val selected = cat == category
                    FilterChip(
                        selected = selected,
                        onClick = { category = cat },
                        label = { Text(cat.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = GlassSurfaceSolid,
                            labelColor = TextSecondary,
                            selectedContainerColor = NeonPurple.copy(alpha = 0.3f),
                            selectedLabelColor = TextPrimary
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selected) NeonPurple else Color.White.copy(alpha = 0.15f)
                        )
                    )
                }
            }

            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberCyan,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                focusedLabelColor = CyberCyan,
                unfocusedLabelColor = TextSecondary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = CyberCyan
            )

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    validationError = null
                },
                label = { Text("Project Title *") },
                singleLine = true,
                colors = fieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_project_title")
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    validationError = null
                },
                label = { Text("Description *") },
                minLines = 3,
                maxLines = 5,
                colors = fieldColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_project_description")
            )

            OutlinedTextField(
                value = techStack,
                onValueChange = { techStack = it },
                label = { Text("Technologies Used (comma separated)") },
                placeholder = { Text("e.g. Kotlin, Compose, AI Tools") },
                singleLine = true,
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = projectUrl,
                onValueChange = { projectUrl = it },
                label = { Text("Project Link / Demo URL (Optional)") },
                placeholder = { Text("https://...") },
                singleLine = true,
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            if (validationError != null) {
                Text(
                    text = validationError.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = RoseAccent
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NeonOutlineButton(
                    text = stringResource(R.string.action_cancel),
                    onClick = onDismiss,
                    accentColor = TextSecondary,
                    modifier = Modifier.weight(1f)
                )
                NeonGradientButton(
                    text = stringResource(R.string.action_save_project),
                    onClick = {
                        when {
                            title.isBlank() -> validationError = "Please enter a project title."
                            description.isBlank() -> validationError = "Please enter a short description."
                            else -> onSave(title, category, description, techStack, projectUrl, pickedImageUri)
                        }
                    },
                    testTag = "btn_save_project",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

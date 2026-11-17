package com.example.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PortfolioData
import com.example.data.ProjectRepository
import com.example.data.SkillCategory
import com.example.data.SkillItem
import com.example.data.local.NihalxDatabase
import com.example.data.local.ProjectCategory
import com.example.data.local.ProjectEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ProjectsUiState {
    data object Loading : ProjectsUiState
    data object Empty : ProjectsUiState
    data class Success(val projects: List<ProjectEntity>) : ProjectsUiState
    data class Error(val message: String) : ProjectsUiState
}

enum class ProjectFilter(val label: String, val categoryKey: String?) {
    ALL("All", null),
    WEBSITE("Websites", ProjectCategory.WEBSITE.name),
    ANDROID("Android Apps", ProjectCategory.ANDROID.name),
    AI_EDITING("AI Editing", ProjectCategory.AI_EDITING.name)
}

class PortfolioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProjectRepository = ProjectRepository(
        projectDao = NihalxDatabase.getInstance(application).projectDao(),
        appContext = application.applicationContext
    )

    private val _isIntroLoading = MutableStateFlow(true)
    val isIntroLoading: StateFlow<Boolean> = _isIntroLoading.asStateFlow()

    private val _selectedSkillCategory = MutableStateFlow(SkillCategory.ALL)
    val selectedSkillCategory: StateFlow<SkillCategory> = _selectedSkillCategory.asStateFlow()

    private val _selectedProjectFilter = MutableStateFlow(ProjectFilter.ALL)
    val selectedProjectFilter: StateFlow<ProjectFilter> = _selectedProjectFilter.asStateFlow()

    private val _expandedSkillId = MutableStateFlow<String?>(PortfolioData.skills.first().id)
    val expandedSkillId: StateFlow<String?> = _expandedSkillId.asStateFlow()

    val filteredSkills: StateFlow<List<SkillItem>> = _selectedSkillCategory
        .combine(MutableStateFlow(PortfolioData.skills)) { category, skills ->
            if (category == SkillCategory.ALL) skills else skills.filter { it.category == category }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PortfolioData.skills
        )

    val projectsUiState: StateFlow<ProjectsUiState> = combine(
        repository.allProjects,
        _selectedProjectFilter
    ) { projects, filter ->
        val filtered = if (filter.categoryKey == null) {
            projects
        } else {
            projects.filter { it.category == filter.categoryKey }
        }
        if (projects.isEmpty()) {
            ProjectsUiState.Empty
        } else {
            ProjectsUiState.Success(filtered)
        }
    }
        .catch { throwable ->
            emit(ProjectsUiState.Error(throwable.localizedMessage ?: "Failed to load projects."))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProjectsUiState.Loading
        )

    init {
        viewModelScope.launch {
            delay(1550L)
            _isIntroLoading.value = false
        }
    }

    fun skipIntro() {
        _isIntroLoading.value = false
    }

    fun selectSkillCategory(category: SkillCategory) {
        _selectedSkillCategory.value = category
    }

    fun toggleSkillExpanded(skillId: String) {
        _expandedSkillId.value = if (_expandedSkillId.value == skillId) null else skillId
    }

    fun selectProjectFilter(filter: ProjectFilter) {
        _selectedProjectFilter.value = filter
    }

    fun saveProject(
        existingId: Int? = null,
        title: String,
        category: ProjectCategory,
        description: String,
        techStack: String,
        projectUrl: String,
        pickedImageUri: Uri?,
        existingImagePath: String = "",
        onComplete: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val finalImagePath = if (pickedImageUri != null) {
                    repository.persistPickedImage(pickedImageUri)
                } else {
                    existingImagePath
                }

                val entity = ProjectEntity(
                    id = existingId ?: 0,
                    title = title.trim(),
                    category = category.name,
                    description = description.trim(),
                    techStack = techStack.trim(),
                    projectUrl = projectUrl.trim(),
                    imagePath = finalImagePath,
                    createdAt = System.currentTimeMillis()
                )

                if (existingId != null && existingId > 0) {
                    repository.updateProject(entity)
                    onComplete(true, "Project updated")
                } else {
                    repository.insertProject(entity)
                    onComplete(true, "Project added to NIHALX")
                }
            } catch (e: Exception) {
                onComplete(false, e.localizedMessage ?: "Could not save project")
            }
        }
    }

    fun deleteProject(project: ProjectEntity, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            runCatching {
                repository.deleteProject(project)
            }.onSuccess {
                onComplete("Project removed")
            }.onFailure {
                onComplete("Failed to remove project")
            }
        }
    }
}

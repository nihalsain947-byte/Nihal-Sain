package com.example.data

import android.content.Context
import android.net.Uri
import com.example.data.local.ProjectDao
import com.example.data.local.ProjectEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ProjectRepository(
    private val projectDao: ProjectDao,
    private val appContext: Context
) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insertProject(project: ProjectEntity) {
        projectDao.insertProject(project)
    }

    suspend fun updateProject(project: ProjectEntity) {
        projectDao.updateProject(project)
    }

    suspend fun deleteProject(project: ProjectEntity) {
        if (project.imagePath.isNotBlank()) {
            withContext(Dispatchers.IO) {
                runCatching {
                    val file = File(project.imagePath)
                    if (file.exists() && file.parentFile?.name == "project_images") {
                        file.delete()
                    }
                }
            }
        }
        projectDao.deleteProjectById(project.id)
    }

    suspend fun persistPickedImage(sourceUri: Uri): String = withContext(Dispatchers.IO) {
        val dir = File(appContext.filesDir, "project_images").apply {
            if (!exists()) mkdirs()
        }
        val destFile = File(dir, "proj_${UUID.randomUUID()}.jpg")
        appContext.contentResolver.openInputStream(sourceUri)?.use { input ->
            FileOutputStream(destFile).use { output ->
                input.copyTo(output)
            }
        }
        destFile.absolutePath
    }
}

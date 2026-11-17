package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ProjectCategory(val displayName: String) {
    WEBSITE("Website"),
    ANDROID("Android App"),
    AI_EDITING("AI Editing")
}

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String,
    val description: String,
    val techStack: String,
    val projectUrl: String = "",
    val imagePath: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

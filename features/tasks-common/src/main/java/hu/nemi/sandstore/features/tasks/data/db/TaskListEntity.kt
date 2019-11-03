package hu.nemi.sandstore.features.tasks.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskListEntity(
    @PrimaryKey val id: String,
    val etag: String?,
    val title: String,
    val updated: String
)


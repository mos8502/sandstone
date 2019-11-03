package hu.nemi.sandstore.features.tasks.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TaskListEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskListId"]
        )
    ]
)
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val taskListId: String,
    val etag: String,
    val title: String,
    val updated: String,
    val notes: String?,
    val status: String,
    val due: String?,
    val completed: String?
)


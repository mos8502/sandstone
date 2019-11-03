package hu.nemi.sandstore.features.tasks.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TaskListWithTasksEntity(
    @Embedded val taskList: TaskListEntity,
    @Relation(
        parentColumn = "id",
        entity = TaskEntity::class,
        entityColumn = "taskListId"
    )
    val tasks: List<TaskEntity>
)

package hu.nemi.sandstone.features.tasks.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    val taskLists: Flow<List<TaskList>>

    fun taskList(taskListId: String): Flow<TaskList?>

    suspend fun fetchTaskList(taskListId: String)

    suspend fun updateTaskLists()
}

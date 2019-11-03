package hu.nemi.sandstore.features.tasks.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TasksDao {
    @get:Query("SELECT * from TaskListEntity")
    abstract val taskLists: Flow<List<TaskListEntity>>

    @Transaction
    @Query("SELECT * FROM TaskListEntity WHERE id=:taskListId")
    abstract fun taskListWithTasks(taskListId: String): Flow<TaskListWithTasksEntity?>

    @Transaction
    open suspend fun writeTaskListWithTasks(taskListWithTasksEntity: TaskListWithTasksEntity) {
        deleteTaskListTasks(taskListWithTasksEntity.taskList.id)
        writeTaskLists(listOf(taskListWithTasksEntity.taskList))
        writeTasks(taskListWithTasksEntity.tasks)
    }

    @Transaction
    open suspend fun updateTaskLists(taskLists: List<TaskListEntity>) {
        deleteTaskLists()
        writeTaskLists(taskLists)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun writeTaskLists(taskLists: List<TaskListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun writeTasks(taskLists: List<TaskEntity>)

    @Query("DELETE FROM TaskEntity WHERE taskListId=:taskListId")
    abstract suspend fun deleteTaskListTasks(taskListId: String)

    @Query("DELETE FROM TaskListEntity")
    protected abstract suspend fun deleteTaskLists()
}
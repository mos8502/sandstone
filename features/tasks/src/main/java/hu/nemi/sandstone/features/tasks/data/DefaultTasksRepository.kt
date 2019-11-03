package hu.nemi.sandstone.features.tasks.data

import com.apollographql.apollo.ApolloClient
import hu.nemi.sandstone.graphql.GetTaskListQuery
import hu.nemi.sandstone.graphql.TaskListPageQuery
import hu.nemi.sandstone.util.await
import hu.nemi.sandstore.features.tasks.data.db.TaskListEntity
import hu.nemi.sandstore.features.tasks.data.db.TasksDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultTasksRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val tasksDao: TasksDao
) : TasksRepository {
    override val taskLists: Flow<List<TaskList>>
        get() = tasksDao.taskLists.map { entities ->
            entities.map(::TaskList)
        }

    override fun taskList(taskListId: String): Flow<TaskList?> =
        tasksDao.taskListWithTasks(taskListId)
            .map { entity -> entity?.let(::TaskList) }

    override suspend fun fetchTaskList(taskListId: String) {
        val query = GetTaskListQuery.builder().taskListId(taskListId).build()
        val taskList = requireNotNull(apolloClient.query(query).await().data()?.taskList())
        tasksDao.writeTaskListWithTasks(
            TaskListWithTasksEntity(
                taskList
            )
        )
    }

    override suspend fun updateTaskLists() {
        var entities = emptyList<TaskListEntity>()
        var pageToken: String? = null
        do {
            val (page, token) = getTaskListPage(pageToken)
            entities = entities + page.map { pageItem ->
                TaskListEntity(
                    pageItem
                )
            }
            pageToken = token
        } while (pageToken != null)

        tasksDao.updateTaskLists(entities)
    }

    private suspend fun getTaskListPage(pageToken: String? = null): Pair<List<TaskListPageQuery.Item>, String?> {
        val query = TaskListPageQuery.builder().pageToken(pageToken).build()
        val page = requireNotNull(apolloClient.query(query).await().data()?.taskLists())
        val items = page.items()
        return items to page.nextPageToken()
    }
}
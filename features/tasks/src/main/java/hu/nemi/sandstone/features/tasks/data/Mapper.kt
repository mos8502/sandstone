package hu.nemi.sandstone.features.tasks.data

import hu.nemi.sandstone.graphql.GetTaskListQuery
import hu.nemi.sandstone.graphql.TaskListPageQuery
import hu.nemi.sandstore.features.tasks.data.db.TaskEntity
import hu.nemi.sandstore.features.tasks.data.db.TaskListEntity
import hu.nemi.sandstore.features.tasks.data.db.TaskListWithTasksEntity

@Suppress("FunctionName")
fun TaskEntity(task: GetTaskListQuery.Task, taskListId: String): TaskEntity =
    TaskEntity(
        id = task.id(),
        taskListId = taskListId,
        etag = task.etag(),
        title = task.title(),
        updated = task.updated(),
        notes = task.notes(),
        status = task.status(),
        due = task.due(),
        completed = task.completed()
    )

@Suppress("FunctionName")
fun TaskListEntity(from: TaskListPageQuery.Item): TaskListEntity =
    TaskListEntity(
        id = from.id(),
        etag = from.etag(),
        title = from.title(),
        updated = from.updated()
    )

@Suppress("FunctionName")
fun TaskListWithTasksEntity(dto: GetTaskListQuery.TaskList): TaskListWithTasksEntity =
    TaskListWithTasksEntity(
        taskList = TaskListEntity(
            id = dto.id(),
            etag = dto.etag(),
            title = dto.title(),
            updated = dto.updated()
        ),
        tasks = dto.tasks().map { taskDto ->
            TaskEntity(
                id = taskDto.id(),
                taskListId = dto.id(),
                etag = taskDto.etag(),
                title = taskDto.title(),
                updated = taskDto.updated(),
                notes = taskDto.notes(),
                status = taskDto.status(),
                due = taskDto.due(),
                completed = taskDto.completed()
            )
        }
    )

@Suppress("FunctionName")
fun Task(entity: TaskEntity): Task =
    Task(
        id = entity.id,
        etag = entity.etag,
        title = entity.title,
        updated = entity.updated,
        notes = entity.notes,
        status = entity.status,
        due = entity.due,
        completed = entity.completed
    )

@Suppress("FunctionName")
fun TaskList(entity: TaskListEntity): TaskList =
    TaskList(
        id = entity.id,
        etag = entity.etag,
        title = entity.title,
        updated = entity.updated,
        tasks = emptyList()
    )

@Suppress("FunctionName")
fun TaskList(entity: TaskListWithTasksEntity): TaskList =
    TaskList(
        id = entity.taskList.id,
        etag = entity.taskList.etag,
        title = entity.taskList.title,
        updated = entity.taskList.updated,
        tasks = entity.tasks.map(::Task)
    )

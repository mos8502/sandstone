package hu.nemi.sandstone.features.tasks.data

data class TaskList(
    val id: String,
    val etag: String?,
    val title: String,
    val updated: String,
    val tasks: List<Task>
)

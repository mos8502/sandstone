package hu.nemi.sandstone.features.tasks.data

data class Task(
    val id: String,
    val etag: String,
    val title: String,
    val updated: String,
    val notes: String?,
    val status: String,
    val due: String?,
    val completed: String?
)

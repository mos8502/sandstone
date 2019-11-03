package hu.nemi.sandstone.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.nemi.sandstore.features.tasks.data.db.TaskListEntity
import hu.nemi.sandstore.features.tasks.data.db.TasksDao
import hu.nemi.sandstore.features.tasks.data.db.TaskEntity

@Database(
    entities = [
        TaskListEntity::class,
        TaskEntity::class
    ],
    version = 1
)
abstract class SandstoneDb : RoomDatabase() {
    abstract val tasks: TasksDao
}
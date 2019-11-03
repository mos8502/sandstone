package hu.nemi.sandstone.features.tasks.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import hu.nemi.sandstone.app.db.SandstoneDb
import hu.nemi.sandstone.features.tasks.ui.TaskListFragment
import hu.nemi.sandstone.features.tasks.ui.TaskListViewModel
import hu.nemi.sandstone.features.tasks.ui.TaskListsFragment
import hu.nemi.sandstone.features.tasks.ui.TaskListsViewModel
import hu.nemi.sandstone.util.FragmentKey
import hu.nemi.sandstone.util.ViewModelFactory
import hu.nemi.sandstone.util.ViewModelKey
import hu.nemi.sandstore.features.tasks.data.db.TasksDao

@Module
class TasksModule {
    @Provides
    @IntoMap
    @ViewModelKey(TaskListsViewModel::class)
    fun taskListsViewModel(instance: TaskListsViewModel): ViewModel = instance

    @Provides
    @IntoMap
    @ViewModelKey(TaskListViewModel::class)
    fun taskListViewModel(instance: TaskListViewModel): ViewModel = instance

    @Provides
    @IntoMap
    @FragmentKey("hu.nemi.sandstone.features.tasks.ui.TaskListsFragment")
    fun taskListsFragment(instance: TaskListsFragment): Fragment = instance

    @Provides
    @IntoMap
    @FragmentKey("hu.nemi.sandstone.features.tasks.ui.TaskListFragment")
    fun taskListFragment(instance: TaskListFragment): Fragment = instance

    @Provides
    fun tasksDao(database: SandstoneDb): TasksDao = database.tasks

    @Provides
    @hu.nemi.sandstone.features.tasks.di.TasksScope
    fun tasksRepository(instance: hu.nemi.sandstone.features.tasks.data.DefaultTasksRepository): hu.nemi.sandstone.features.tasks.data.TasksRepository = instance

    @Provides
    fun viewModelFactory(instance: ViewModelFactory): ViewModelProvider.Factory = instance
}
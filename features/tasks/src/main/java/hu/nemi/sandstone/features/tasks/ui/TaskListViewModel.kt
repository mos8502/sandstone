package hu.nemi.sandstone.features.tasks.ui

import androidx.lifecycle.*
import hu.nemi.sandstone.features.tasks.data.TaskList
import hu.nemi.sandstone.features.tasks.data.TasksRepository
import hu.nemi.sandstone.util.Lce
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskListViewModel @Inject constructor(private val provider: TasksRepository) : ViewModel() {
    private val taskListId = MutableLiveData<String>()

    val taskList: LiveData<Lce<TaskList>> = Transformations.switchMap(taskListId) { taskListId ->
        object : LiveData<Lce<TaskList>>(Lce.Loading) {
            private var job: Job? = null

            override fun onActive() {
                job = viewModelScope.launch {
                    provider.taskList(taskListId).collect { taskList ->
                        if (taskList != null)
                            value = Lce.Content(taskList)
                    }
                }
            }
        }
    }

    fun loadTaskList(id: String) {
        if (taskListId.value != id || taskList.value is Lce.Error) {
            taskListId.value = id
            viewModelScope.launch {
                try {
                    provider.fetchTaskList(id)
                } catch (error: Throwable) {
                    error.printStackTrace()
                }
            }
        }
    }
}
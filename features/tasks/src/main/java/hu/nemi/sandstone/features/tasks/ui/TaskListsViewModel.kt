package hu.nemi.sandstone.features.tasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashlytics.android.Crashlytics
import hu.nemi.sandstone.features.tasks.data.TaskList
import hu.nemi.sandstone.features.tasks.data.TasksRepository
import hu.nemi.sandstone.util.Lce
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskListsViewModel @Inject constructor(private val repository: TasksRepository) :
    ViewModel() {
    private var refreshJob: Job? = null

    val taskLists = object : LiveData<Lce<List<TaskList>>>(Lce.Loading) {
        private var taskList: Job? = null

        override fun onActive() {
            taskList = viewModelScope.launch {
                repository.taskLists.collect { taskLists ->
                    value = Lce.Content(taskLists)

                }
            }
        }

        override fun onInactive() {
            taskList?.cancel()
        }
    }

    init {
        refreshList()
    }

    private fun refreshList() {
        refreshJob?.cancel()
        viewModelScope.launch {
            try {
                repository.updateTaskLists()
            } catch (error: Throwable) {
                Crashlytics.log(Log.ERROR,
                    LOG_TAG, "updateTaskLists() failed")
                Crashlytics.logException(error)
            }
        }
    }
}

private const val LOG_TAG = "TaskListsViewModel"
package hu.nemi.sandstone.features.tasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hu.nemi.sandstone.features.tasks.R
import hu.nemi.sandstone.util.Lce
import kotlinx.android.synthetic.main.fragment_task_lists.*
import javax.inject.Inject

class TaskListFragment @Inject constructor(viewModelFactory: ViewModelProvider.Factory) :
    Fragment() {
    private val viewModel by viewModels<TaskListViewModel> { viewModelFactory }

    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_task_lists, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.fargment_title_tasks_lists)
        taskLists.adapter = adapter
        taskLists.layoutManager = LinearLayoutManager(view.context)
        viewModel.taskList.observe(viewLifecycleOwner, Observer { lce ->
            if (lce is Lce.Content<hu.nemi.sandstone.features.tasks.data.TaskList>) {
                toolbar.title = lce.value.title
                adapter.submitList(lce.value.tasks)
            }
        })
        viewModel.loadTaskList(
            TaskListFragmentArgs.fromBundle(
                requireNotNull(arguments)
            ).taskListId)
    }
}
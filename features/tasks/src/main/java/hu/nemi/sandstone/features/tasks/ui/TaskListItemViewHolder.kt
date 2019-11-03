package hu.nemi.sandstone.features.tasks.ui

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.nemi.sandstone.features.tasks.data.TaskList
import kotlinx.android.synthetic.main.view_task_list_item.view.*

class TaskListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.title

    fun bind(taskList: TaskList) {
        title.text = taskList.title
        itemView.setOnClickListener {
            val navController = itemView.findNavController()
            val direction =
                TaskListsFragmentDirections.actionTaskListsToTasklist(
                    taskList.id
                )
            navController.navigate(direction)
        }
    }
}
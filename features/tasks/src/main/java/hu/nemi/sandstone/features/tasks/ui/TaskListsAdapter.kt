package hu.nemi.sandstone.features.tasks.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import hu.nemi.sandstone.features.tasks.R
import hu.nemi.sandstone.features.tasks.data.TaskList

class TaskListsAdapter : ListAdapter<TaskList, TaskListItemViewHolder>(
    TaskListItemDiffer
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.view_task_list_item, parent, false)
        return TaskListItemViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: TaskListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object TaskListItemDiffer : DiffUtil.ItemCallback<TaskList>() {
    override fun areItemsTheSame(oldItem: TaskList, newItem: TaskList): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TaskList, newItem: TaskList): Boolean =
        oldItem == newItem
}
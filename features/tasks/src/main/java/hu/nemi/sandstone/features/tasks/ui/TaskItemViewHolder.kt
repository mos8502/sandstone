package hu.nemi.sandstone.features.tasks.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hu.nemi.sandstone.features.tasks.data.Task
import kotlinx.android.synthetic.main.view_task_item.view.*

class TaskItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.title

    fun bind(task: Task) {
        title.text = task.title
    }
}
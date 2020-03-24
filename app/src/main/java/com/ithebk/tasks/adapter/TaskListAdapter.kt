package com.ithebk.tasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ithebk.tasks.R
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task
import kotlinx.android.synthetic.main.layout_task.view.*

class TaskListAdapter internal constructor(
    context: Context,
    private val callback: MainItemViewClickCallback
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>() // Cached copy of words

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskItemView: TextView = itemView.findViewById(R.id.textView)
        val statusImage: ImageView = itemView.findViewById(R.id.task_card_status_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.layout_task, parent, false)
        return TaskViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = tasks[position]
        holder.taskItemView.text = current.info
        if(current.done) {
            holder.statusImage.setImageResource(R.drawable.ic_task_in_active)
        }
        else {
            holder.statusImage.setImageResource(R.drawable.ic_task_active)
        }
        holder.itemView.task_card_status_frame.setOnClickListener{
            callback.onItemClick(position, current)
        }

        holder.itemView.setOnLongClickListener {
            callback.onItemLongClick(position, current)
            false;
        }
    }

    internal fun setWords(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() :Int {
        println("Tasks Length:"+tasks.size)
        return tasks.size;
    }
}
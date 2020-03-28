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
import kotlinx.android.synthetic.main.empty_placeholde_layout.view.*
import kotlinx.android.synthetic.main.layout_task.view.*

class TaskListAdapter internal constructor(
    context: Context,
    private val callback: MainItemViewClickCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        var ITEM_VIEW_EMPTY = 1
        var ITEM_VIEW_TASK = 2
        var IMAGES = arrayOf( R . drawable . ic_empty_place_holder_1,
        R.drawable.ic_empty_place_holder_2,
        R.drawable.ic_empty_place_holder_3)

    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>() // Cached copy of words
    private var isLongPressActivated = false

//    var typedValue : TypedValue = TypedValue();
//    var theme = context.theme.resolveAttribute(R.attr.selected_color, typedValue, true)
//    var color = typedValue.data

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskItemView: TextView = itemView.findViewById(R.id.textView)
        val statusImage: ImageView = itemView.findViewById(R.id.task_card_status_image)
    }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emptyImageView : ImageView = itemView.findViewById(R.id.image_empty_placeholder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VIEW_EMPTY) {
            return EmptyViewHolder(
                inflater.inflate(
                    R.layout.empty_placeholde_layout,
                    parent,
                    false
                )
            )
        }
        return TaskViewHolder(inflater.inflate(R.layout.layout_task, parent, false))
    }

    private fun onSelectItem(task: Task, position: Int) {
        task.selected = !task.selected
        notifyItemChanged(position)
        if(tasks.none { it.selected }) {
            isLongPressActivated = false
        }
        callback.onItemLongClick(tasks.filter { it.selected })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TaskViewHolder) {
            val current = tasks[position]
            holder.taskItemView.text = current.info

            holder.itemView.task_card_status_frame.setOnClickListener {
                when {
                    !isLongPressActivated -> {
                        callback.onItemCircleClick(position, current)
                    }
                }
            }
            holder.itemView.card_layout_task.setOnClickListener {
                when {
                    isLongPressActivated -> {
                        onSelectItem(current, position)
                    }
                    else -> {
                        callback.onItemClick(position, current)
                    }
                }
            }

            holder.itemView.card_layout_task.setOnLongClickListener {
                //callback.onItemLongClick(position, current)
                if(!isLongPressActivated) {
                    isLongPressActivated = true
                }
                onSelectItem(current, position)
                false
            }

            if(current.selected) {
                holder.itemView.card_layout_task.elevation = 10f
                holder.statusImage.setImageResource(R.drawable.ic_check_circle_24dp)
            }
            else {
                holder.itemView.card_layout_task.elevation = 1f
                if (current.done) {
                    holder.statusImage.setImageResource(R.drawable.ic_task_in_active)
                } else {
                    holder.statusImage.setImageResource(R.drawable.ic_task_active)
                }
            }
        }
        else if(holder is EmptyViewHolder) {
            holder.itemView.image_empty_placeholder.setImageResource(IMAGES[position])
        }

    }

    internal fun setWords(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (tasks.isEmpty()) {
            return ITEM_VIEW_EMPTY
        }
        return ITEM_VIEW_TASK
    }

    override fun getItemCount(): Int {
//        println("Tasks Length:" + tasks.size)
//        if (tasks.isEmpty()) {
//            return IMAGES.size
//        }
        return tasks.size
    }
}
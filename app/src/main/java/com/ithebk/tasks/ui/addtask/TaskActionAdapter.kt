package com.ithebk.tasks.ui.addtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ithebk.tasks.R
import com.ithebk.tasks.callbacks.ActionCallback
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task
import com.ithebk.tasks.models.TaskAction

class TaskActionAdapter internal constructor(
    context: Context,
    taskActions: List<TaskAction>,
    private val callback: ActionCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        var ITEM_VIEW_ACTION_BUTTON = 1
        var ITEM_VIEW_ACTION_VALUE = 2

    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var actions = taskActions // Cached copy of words

    inner class ActionButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvActionName : TextView = itemView.findViewById(R.id.text_action_name)
        var imageAction : ImageView = itemView.findViewById(R.id.image_action)
    }
    inner class ActionValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VIEW_ACTION_BUTTON) {
            return ActionButtonViewHolder(
                inflater.inflate(
                    R.layout.layout_action_button,
                    parent,
                    false
                )
            )
        }
        return ActionValueViewHolder(inflater.inflate(R.layout.layout_action_value, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActionButtonViewHolder) {
            holder.tvActionName.text = actions.get(position).value
            holder.imageAction.setImageResource(actions.get(position).icon)
        }
        else if(holder is ActionValueViewHolder) {

        }

    }


    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_ACTION_BUTTON
    }

    override fun getItemCount(): Int {
        return actions.size
    }
}
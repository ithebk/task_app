package com.ithebk.tasks.ui.addtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ithebk.tasks.R
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task

class TaskActionAdapter internal constructor(
    context: Context,
    private val callback: MainItemViewClickCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        var ITEM_VIEW_ACTION_BUTTON = 1
        var ITEM_VIEW_ACTION_VALUE = 2

    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>() // Cached copy of words

    inner class ActionButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

        }
        else if(holder is ActionValueViewHolder) {

        }

    }


    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_ACTION_BUTTON
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}
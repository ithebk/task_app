package com.ithebk.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import kotlinx.android.synthetic.main.layout_task.view.*

class MainItemAdapter(private val dataSets: Array<String>, private val callback: MainItemViewClickCallback) :
    RecyclerView.Adapter<MainItemAdapter.MainItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        return MainItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_task, parent, false))
    }

    override fun getItemCount() = dataSets.size

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.itemView.textView.text = dataSets[position]
        holder.itemView.setOnClickListener {
            callback.onItemClick(position, dataSets[position])
        }
    }

    class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}
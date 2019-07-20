package com.ithebk.tasks.callbacks

import com.ithebk.tasks.db.Task


interface MainItemViewClickCallback {
    fun onItemClick(position: Int, text: Task) {
    }fun onItemLongClick(position: Int, text: Task) {
    }
}
package com.ithebk.tasks.callbacks

import com.ithebk.tasks.models.TaskAction


interface ActionCallback {
    fun onItemClick(position: Int, taskAction: TaskAction)

}
package com.ithebk.tasks

import com.ithebk.tasks.models.EXTRA_ACTION
import com.ithebk.tasks.models.TaskAction

class Utils {
    companion object {
        val ACTIONS = arrayListOf<TaskAction>(
            TaskAction(
                EXTRA_ACTION.NOTIFICATION,
                "Add Notification",
                R.drawable.ic_delete_16dp,
                false
            ),
            TaskAction(
                EXTRA_ACTION.DUE_DATE,
                "Add due date",
                R.drawable.ic_due_date_24dp,
                false
            ),
            TaskAction(
                EXTRA_ACTION.REPEAT,
                "Repeat",
                R.drawable.ic_due_date_24dp,
                false
            ),
            TaskAction(
                EXTRA_ACTION.LOG_TIME,
                "Log time",
                R.drawable.ic_due_date_24dp,
                false
            )
        )
    }
}
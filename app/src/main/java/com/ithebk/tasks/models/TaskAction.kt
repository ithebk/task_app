package com.ithebk.tasks.models
enum class EXTRA_ACTION {
    NOTIFICATION,
    DUE_DATE,
    REPEAT,
    LOG_TIME
}
data class TaskAction(
    var action: EXTRA_ACTION,
    var value: String,
    var icon: Int,
    var isActive: Boolean
)
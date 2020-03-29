package com.ithebk.tasks.models
enum class EXTRA_ACTION {
    ADD_NOTIFICATION,
    REMOVE_NOTIFICATION,
    ADD_DUE_DATE,
    REMOVE_DUE_DATE,
    ADD_REPEAT,
    REMOVE_REPEAT,
    ADD_LOG_TIME,
    REMOVE_LOG_TIME,
}
enum class EXTRA_ACTION_TYPE {
    ADD,
    REMOVE
}
data class TaskAction(
    var action: EXTRA_ACTION,
    var actionType: EXTRA_ACTION_TYPE,
    var value: String,
    var icon: Int
)
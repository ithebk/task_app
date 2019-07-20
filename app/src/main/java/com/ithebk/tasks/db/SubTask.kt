package com.ithebk.tasks.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sub_task_table",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Task::class,
            parentColumns = arrayOf("taskId"),
            childColumns = arrayOf("subTaskId"),
            onDelete = ForeignKey.CASCADE
        )
    )
) data class SubTask(
    @PrimaryKey(autoGenerate = true)
    val subTaskId: Long,
    val created_at: Long,
    val modified_at: Long,
    val info: String
)

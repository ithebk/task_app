package com.ithebk.tasks.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId : Long,
    val created_at: Long,
    val modified_at: Long,
    val info: String,
    var done : Boolean = false,
    val deleted : Boolean = false
    )

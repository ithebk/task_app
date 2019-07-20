package com.ithebk.tasks.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(val created_at: Long, @PrimaryKey val info: String)
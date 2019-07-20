package com.ithebk.tasks.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("SELECT * from task_table ORDER BY info ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE FROM task_table")
    fun deleteAll()



}
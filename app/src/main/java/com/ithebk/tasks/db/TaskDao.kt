package com.ithebk.tasks.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("SELECT * from task_table ORDER BY modified_at DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE FROM task_table")
    fun deleteAll()

    @Delete()
    fun delete(task: Task)

    @Query("SELECT * from task_table WHERE info LIKE :query " + "ORDER BY modified_at DESC")
    fun query(query: String?) : LiveData<List<Task>>



}
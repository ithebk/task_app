package com.ithebk.tasks.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubTaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subTask: SubTask)

    @Query("SELECT * from sub_task_table WHERE subTaskId = :parentTaskId")
    fun getAllSubTasks(parentTaskId: Long): LiveData<List<SubTask>>

    @Query("DELETE FROM sub_task_table WHERE subTaskId = :parentTaskId")
    fun deleteAll(parentTaskId: Long)


}
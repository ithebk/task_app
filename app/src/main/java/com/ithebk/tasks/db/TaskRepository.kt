package com.ithebk.tasks.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao : TaskDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTasks : LiveData<List<Task>> = taskDao.getAllTasks();

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    // This ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    suspend fun insert(task: Task) {
        taskDao.insert(task);
    }
    suspend fun delete(task: Task) {
        taskDao.delete(task);
    }
    suspend fun query(q: String) {
        taskDao.query(q);
    }
}
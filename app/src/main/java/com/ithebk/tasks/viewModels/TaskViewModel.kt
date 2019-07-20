package com.ithebk.tasks.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ithebk.tasks.db.Task
import com.ithebk.tasks.db.TaskRepository
import com.ithebk.tasks.db.TaskRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application : Application) : AndroidViewModel(application) {

    private val repository : TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskRoomDatabase.getDatabase(application, viewModelScope).taskDao();
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task : Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }
}
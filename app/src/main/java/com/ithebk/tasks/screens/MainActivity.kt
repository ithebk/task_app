package com.ithebk.tasks.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ithebk.tasks.R
import com.ithebk.tasks.adapter.TaskListAdapter
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task
import com.ithebk.tasks.viewModels.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewAdapter: TaskListAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapter()
        setupViewModelProvider()

        add_task_fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    private fun setupViewModelProvider() {
        // Get a new or existing ViewModel from the ViewModelProvider.
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        // Add an observer on the LiveData returned by getAll function.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            // Update the cached copy of the task in the adapter.
            tasks?.let { viewAdapter.setWords(it) }
        })
    }

    private fun setAdapter() {
        viewAdapter = TaskListAdapter(this, object : MainItemViewClickCallback {
            override fun onItemClick(position: Int, task: Task) {
                task.done = !task.done;
                addOrUpdateTask(task, "")
            }
            override fun onItemLongClick(position: Int, task: Task) {
                delete(task)
            }
        })
       // viewManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewManager = LinearLayoutManager(this)
        task_main_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun delete(task: Task) {
        taskViewModel.delete(task)
    }

    private fun addOrUpdateTask(taskPrev: Task?, stringExtra: String) {
        val task = taskPrev?:Task(
            0,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            stringExtra
        )
        taskViewModel.insert(task)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                addOrUpdateTask(null, it.getStringExtra(AddTaskActivity.EXTRA_REPLY))
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Empty data not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    companion object {
        const val newWordActivityRequestCode = 1
    }
}
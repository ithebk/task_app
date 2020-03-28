package com.ithebk.tasks.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ithebk.tasks.R
import com.ithebk.tasks.adapter.TaskListAdapter
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task
import com.ithebk.tasks.viewModels.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AddTaskBottomDialogFragment.AddTaskBottomSheetListener {
    private lateinit var viewAdapter: TaskListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapter()
        setupViewModelProvider()

        add_task_fab.setOnClickListener {
//            val intent = Intent(thisfr@MainActivity, AddTaskActivity::class.java)
//            startActivityForResult(intent, newWordActivityRequestCode)
            AddTaskBottomDialogFragment.newInstance().show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)

        }

        bt_action_more.setOnClickListener {
            AddTaskBottomDialogFragment().show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
        }

        search_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun setupViewModelProvider() {
        // Get a new or existing ViewModel from the ViewModelProvider.
        taskViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(TaskViewModel ::class.java)

        // Add an observer on the LiveData returned by getAll function.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            // Update the cached copy of the task in the adapter.
            tasks?.let { viewAdapter.setWords(it) }
            text_view_tasks_done.text =
                tasks.filter { it.done }.size.toString() + " Tasks completed"
            text_view_tasks_not_done.text =
                tasks.filter { !it.done }.size.toString() + " Yet to finish"
        })
    }

    private fun setAdapter() {
        viewAdapter = TaskListAdapter(this, object : MainItemViewClickCallback {
            override fun onItemClick(position: Int, task: Task) {
                openBottomTaskViewer(position, task)
            }

            override fun onItemLongClick(position: Int, task: Task) {
                //delete(task)
            }

            override fun onItemCircleClick(position: Int, task: Task) {
                task.done = !task.done
                addOrUpdateTask(task, "")
            }
        })
        viewManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // viewManager = LinearLayoutManager(this)
        task_main_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun openBottomTaskViewer(position: Int, task: Task) {
        AddTaskBottomDialogFragment.newInstance(task).show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
    }

    private fun delete(task: Task) {
        taskViewModel.delete(task)
    }

    private fun addOrUpdateTask(taskPrev: Task?, stringExtra: String) {
        val task = taskPrev ?: Task(
            0,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            stringExtra
        )
        taskViewModel.insert(task)
    }


    override fun onSave(name: String, action: ACTION, prevTask: Task?) {
        if(name.isNotEmpty()) {
            if(action == ACTION.NEW) {
                name?.let {
                    addOrUpdateTask(null, name)
                }
            }
            else if(action == ACTION.UPDATE) {
                if (prevTask != null) {
                    prevTask.info = name
                    name?.let {
                        addOrUpdateTask(prevTask, "")
                    }
                }
            }
            else if(action == ACTION.DELETE) {
                if (prevTask != null) {
                    delete(prevTask)
                }
            }

        }
        else {
            Toast.makeText(
                applicationContext,
                "Empty data not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
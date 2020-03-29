package com.ithebk.tasks.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ithebk.tasks.R
import com.ithebk.tasks.callbacks.MainItemViewClickCallback
import com.ithebk.tasks.db.Task
import com.ithebk.tasks.ui.addtask.ACTION
import com.ithebk.tasks.ui.addtask.AddTaskBottomDialogFragment
import com.ithebk.tasks.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AddTaskBottomDialogFragment.AddTaskBottomSheetListener {
    private lateinit var viewAdapter: TaskListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainViewModel: MainViewModel
    private var emptyTaskFragment : EmptyTaskFragment =
        EmptyTaskFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAdapter()
        setupViewModelProvider()

        add_task_fab.setOnClickListener {
            AddTaskBottomDialogFragment.newInstance().show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
        }

        bt_action_more.setOnClickListener {
            //EmptyTaskFragment().show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
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
        mainViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(MainViewModel ::class.java)

        // Add an observer on the LiveData returned by getAll function.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mainViewModel.allTasks.observe(this, Observer { tasks ->
            // Update the cached copy of the task in the adapter.
            tasks?.let { viewAdapter.setWords(it) }
            text_view_tasks_done.text =
                tasks.filter { it.done }.size.toString() + " Tasks completed"
            text_view_tasks_not_done.text =
                tasks.filter { !it.done }.size.toString() + " Yet to finish"
            if(tasks.isEmpty()) {
                supportFragmentManager.beginTransaction().replace(R.id.frame_main, emptyTaskFragment).commit()
            }
            else {
                supportFragmentManager.beginTransaction().remove(emptyTaskFragment).commit()
            }
        })
    }

    private fun setAdapter() {
        viewAdapter = TaskListAdapter(
            this,
            object : MainItemViewClickCallback {
                override fun onItemClick(position: Int, task: Task) {
                    openBottomTaskViewer(position, task)
                }

                override fun onItemLongClick(tasks: List<Task>) {
                    if (tasks.isEmpty()) {
                        image_main_action.setImageResource(R.drawable.ic_add_24dp)
                        add_task_fab.setCardBackgroundColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.colorAccent
                            )
                        )
                        text_main_action.text = "Add new task"
                        add_task_fab.setOnClickListener {
                            AddTaskBottomDialogFragment.newInstance()
                                .show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
                        }
                    } else {
                        image_main_action.setImageResource(R.drawable.ic_delete_24dp)
                        add_task_fab.setCardBackgroundColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.delete_red
                            )
                        )
                        text_main_action.text = "Delete All"
                        add_task_fab.setOnClickListener {
                            println("Delete All")
                            //AddTaskBottomDialogFragment.newInstance().show(supportFragmentManager, AddTaskBottomDialogFragment.TAG)
                        }
                    }
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
        mainViewModel.delete(task)
    }

    private fun addOrUpdateTask(taskPrev: Task?, stringExtra: String) {
        val task = taskPrev ?: Task(
            0,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            stringExtra
        )
        mainViewModel.insert(task)
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
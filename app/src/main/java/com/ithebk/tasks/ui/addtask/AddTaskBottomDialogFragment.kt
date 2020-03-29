package com.ithebk.tasks.ui.addtask

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ithebk.tasks.R
import com.ithebk.tasks.db.Task

enum class ACTION {
    NEW,
    UPDATE,
    DELETE
}

class AddTaskBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var mListener: AddTaskBottomSheetListener
    private lateinit var editWordView: EditText
    private lateinit var btDeleteTask: CardView
    private lateinit var textAddOrUpdate: TextView
    private var action: ACTION =
        ACTION.NEW
    private var currentTask: Task? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.add_task_bottom_layout_sheet, container, false)
        editWordView = rootView.findViewById(R.id.edit_word)
        btDeleteTask = rootView.findViewById(R.id.bt_delete_task)
        textAddOrUpdate = rootView.findViewById(R.id.text_view_add_or_update)
        rootView.findViewById<CardView>(R.id.bt_add_task).setOnClickListener {
            dismiss()
        }
        if ((arguments?.get(TASK)) != null) {
            currentTask = (arguments?.get(TASK)) as Task;
            if(currentTask!=null) {
                textAddOrUpdate.text = getString(R.string.update)
                action = ACTION.UPDATE
                editWordView.setText(currentTask!!.info)
                btDeleteTask.visibility = View.VISIBLE
            }
        }

        btDeleteTask.setOnClickListener {
            action = ACTION.DELETE
            dismiss()
        }


        return rootView
    }


    companion object {
        private const val TASK = "task"
        const val TAG = "MAIN_BOTTOM_DIALOG_FRAGMENT"
        fun newInstance(task: Task? = null) = AddTaskBottomDialogFragment()
            .apply {
            arguments = bundleOf(
                TASK to task
            )
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        mListener.onSave(
            editWordView.text.toString(),
            action,
            currentTask
        )
        super.onDismiss(dialog)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AddTaskBottomSheetListener) {
            mListener = context
        } else {
            throw RuntimeException(
                "$context must implement ItemClickListener"
            )
        }

    }

    interface AddTaskBottomSheetListener {
        fun onSave(name: String, action: ACTION, prevTask: Task?)
    }
}
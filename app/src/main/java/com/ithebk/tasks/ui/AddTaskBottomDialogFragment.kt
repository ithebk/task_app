package com.ithebk.tasks.ui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ithebk.tasks.R
import kotlinx.android.synthetic.main.fragment_layout_add_task.*

class AddTaskBottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var mListener: AddTaskBottomSheetListener
    private lateinit var editWordView: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_layout_add_task, container, false)
        editWordView = rootView.findViewById(R.id.edit_word)
        rootView.findViewById<CardView>(R.id.bt_add_task).setOnClickListener {
            dismiss()
        }
        return rootView
    }

    companion object {
        const val TAG = "MAIN_BOTTOM_DIALOG_FRAGMENT"
    }

    override fun onDismiss(dialog: DialogInterface) {
        mListener.onSave( editWordView.text.toString())
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
        fun onSave(name:String)
    }
}
package com.ithebk.tasks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ithebk.tasks.R

class MainBottomDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_bottom_layout_sheet, container, false)
    }

    companion object {
        const val TAG = "MAIN_BOTTOM_DIALOG_FRAGMENT"
    }
}
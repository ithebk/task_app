package com.ithebk.tasks.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ithebk.tasks.R

class AddTaskActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task_bottom_layout_sheet)
        editWordView = findViewById(R.id.edit_word)

    }

    companion object {
        const val EXTRA_REPLY = "com.ithebk.tasks.REPLY"
    }

    override fun onBackPressed() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(editWordView.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val word = editWordView.text.toString()
            replyIntent.putExtra(EXTRA_REPLY, word)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        super.onBackPressed()
    }

}
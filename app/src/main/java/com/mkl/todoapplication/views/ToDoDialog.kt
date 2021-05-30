package com.mkl.todoapplication.views

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.mkl.todoapplication.R

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
class ToDoDialog(builder: Builder) : Dialog(builder.context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        setContentView(R.layout.todo_dialog)
    }

    init {
        window?.apply {
            setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            //setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    init {
        val editText = findViewById<EditText>(R.id.editText_todo)
        findViewById<Button>(R.id.button).setOnClickListener {
            val text = editText.text.toString()
            if(text.isNotBlank() || text.isNotEmpty()){
                builder.listener?.invoke(text)
                cancel()
            }
        }
    }

    class Builder(val context: Context) {
        var listener: ((String) -> Unit)? = null

        fun registerListener(listener: (String) -> Unit): Builder {
            this.listener = listener
            return this
        }

        fun build() = ToDoDialog(this)
    }
}
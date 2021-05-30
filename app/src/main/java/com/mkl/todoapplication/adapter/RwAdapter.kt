package com.mkl.todoapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mkl.todoapplication.R
import com.mkl.todoapplication.model.ToDoItem

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */

class RwAdapter : RecyclerView.Adapter<RwAdapter.ToDoHolder>() {

    private val dataList = mutableListOf<ToDoItem>()
    private var checkListener: ((isChecked: Boolean, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        return ToDoHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        val item = dataList[position]
        holder.checkedBox.isChecked = item.isChecked
        holder.text.text = item.text
        holder.checkedBox.setOnCheckedChangeListener { buttonView, isChecked ->
            checkListener?.invoke(isChecked, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: List<ToDoItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }
    fun registerIsCheckedListener(listener: (Boolean, Int) -> Unit) {
        checkListener = listener
    }

    inner class ToDoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkedBox = view.findViewById<CheckBox>(R.id.list_item_checkBox)
        val text = view.findViewById<TextView>(R.id.list_item_text)
    }
}

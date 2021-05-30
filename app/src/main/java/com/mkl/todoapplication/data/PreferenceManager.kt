package com.mkl.todoapplication.data

import android.content.Context
import com.mkl.todoapplication.model.ToDoItem

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
interface PreferenceManager {
    fun initializePreferences(context: Context)
    fun saveToDo(todo: ToDoItem)
    fun saveAll(list: List<ToDoItem>)
    fun loadTodo(): List<ToDoItem>
}
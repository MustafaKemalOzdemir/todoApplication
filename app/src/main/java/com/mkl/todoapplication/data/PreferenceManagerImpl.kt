package com.mkl.todoapplication.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mkl.todoapplication.model.ToDoItem
import java.lang.reflect.Type

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
class PreferenceManagerImpl : PreferenceManager {
    companion object {
        private const val PREF_NAME = "ToDoPref"
        private const val DATA_NAME = "ToDoList"
        private lateinit var instance: PreferenceManagerImpl

        fun getInstance(): PreferenceManager {
            return if (this::instance.isInitialized) {
                instance
            } else {
                instance = PreferenceManagerImpl()
                instance
            }
        }
    }

    lateinit var preferences: SharedPreferences

    override fun initializePreferences(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun saveAll(list: List<ToDoItem>) {
        val gson = Gson()
        val rawList = gson.toJson(list)
        preferences.edit().putString(DATA_NAME, rawList).apply()
    }

    override fun saveToDo(todo: ToDoItem) {
        val todolist = loadTodo().toMutableList()
        var contains = false
        todolist.forEach {
            if(it.Id == todo.Id){
                it.isChecked = todo.isChecked
                it.text = todo.text
                contains = true
            }
        }
        if(!contains){
            todolist.add(todo)
        }

        val gson = Gson()
        val rawList = gson.toJson(todolist)
        preferences.edit().putString(DATA_NAME, rawList).apply()
    }

    override fun loadTodo(): List<ToDoItem> {
        val todoListRaw = preferences.getString(DATA_NAME, "") ?: ""
        val gson = Gson()
        val type: Type = object : TypeToken<List<ToDoItem>>() {}.type

        return if (todoListRaw.isEmpty()) {
            listOf<ToDoItem>()
        } else {
            gson.fromJson<List<ToDoItem>>(todoListRaw, type)
        }
    }


}
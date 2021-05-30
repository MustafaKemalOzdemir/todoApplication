package com.mkl.todoapplication

import com.mkl.todoapplication.model.ToDoItem

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
interface ActivityView {
    fun showDialog()
    fun updateAdapterList(list: List<ToDoItem>)
}
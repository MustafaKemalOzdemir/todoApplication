package com.mkl.todoapplication

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
interface ToDoPresenter {
    fun onCreate()
    fun addButtonPressed()
    fun onDialogClosed(text: String)
    fun onSearchTextChanged(text: String)
    fun itemChecked(isChecked: Boolean, index: Int)
    fun itemSwiped(index: Int)
}
package com.mkl.todoapplication

import android.util.Log
import com.mkl.todoapplication.data.PreferenceManager
import com.mkl.todoapplication.data.PreferenceManagerImpl
import com.mkl.todoapplication.model.ToDoItem
import java.util.*

/**
Created by Mustafa Kemal ÖZDEMİR on 30.05.2021
 */
class ToDoPresenterImpl(val view: ActivityView) : ToDoPresenter {
    val interactor: ToDoInteractor = ToDoInteractorImpl()
    private val preferenceManager: PreferenceManager = PreferenceManagerImpl.getInstance()
    private var primaryList = mutableListOf<ToDoItem>()
    private var filteredList = mutableListOf<ToDoItem>()
    private var searchText: String = ""

    override fun addButtonPressed() {
        view.showDialog()
    }

    override fun onCreate() {
        primaryList.addAll(preferenceManager.loadTodo())
        filterAndNotify()
    }

    override fun onDialogClosed(text: String) {
        val id = generateUniqueId()
        val item = ToDoItem(id, text, false)
        preferenceManager.saveToDo(item)
        primaryList.add(item)
        filterAndNotify()
    }

    override fun onSearchTextChanged(text: String) {
        searchText = text.toLowerCase(Locale.ENGLISH).trim()
        filterAndNotify()
    }

    private fun filterAndNotify() {
        filteredList.clear()
        if (searchText.isEmpty() || searchText.isBlank()) {
            view.updateAdapterList(primaryList)
            filteredList.addAll(primaryList)
            return
        }
        primaryList.forEach {
            if (it.text.toLowerCase(Locale.ENGLISH).trim().contains(searchText)) {
                filteredList.add(it)
            }
        }
        view.updateAdapterList(filteredList)
    }

    override fun itemChecked(isChecked: Boolean, index: Int) {
        Log.v(
            "testt",
            "isChecked $isChecked position $index index: ${filteredList[index].Id} isChecked ${filteredList[index].isChecked}"
        )
        filteredList[index].isChecked = isChecked
        preferenceManager.saveAll(primaryList)
    }

    override fun itemSwiped(index: Int) {
        val id = filteredList[index].Id
        primaryList.removeIf { i -> i.Id == id }
        preferenceManager.saveAll(primaryList)
        filterAndNotify()
    }

    private fun generateUniqueId(): Int {
        val idList = primaryList.map { i -> i.Id }.toList()
        val sortedList = idList.sorted().toList()
        var generatedId = -1
        for (index in sortedList.indices) {
            if (index != sortedList[index]) {
                generatedId = index
            }
        }
        if (generatedId == -1) {
            generatedId = idList.size
        }
        return generatedId
    }
}
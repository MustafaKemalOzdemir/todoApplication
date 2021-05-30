package com.mkl.todoapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mkl.todoapplication.adapter.RwAdapter
import com.mkl.todoapplication.data.PreferenceManagerImpl
import com.mkl.todoapplication.databinding.ActivityMainBinding
import com.mkl.todoapplication.model.ToDoItem
import com.mkl.todoapplication.views.ToDoDialog

class MainActivity : AppCompatActivity(), ActivityView {

    private val presenter: ToDoPresenter = ToDoPresenterImpl(this)

    private lateinit var binding: ActivityMainBinding
    private val rwAdapter = RwAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManagerImpl.getInstance().initializePreferences(this)
        initializeView(layoutInflater)
        setContentView(binding.root)
        initializeInteractions()
        presenter.onCreate()
    }

    private fun initializeView(layoutInflater: LayoutInflater) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val context = this
        binding.apply {
            todoRecycler.adapter = rwAdapter
            todoRecycler.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initializeInteractions() {
        binding.apply {
            addButton.setOnClickListener {
                presenter.addButtonPressed()
            }
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.onSearchTextChanged(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

        val itemTouchHelperActivity = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    presenter.itemSwiped(viewHolder.adapterPosition)
                }
            })
        itemTouchHelperActivity.attachToRecyclerView(binding.todoRecycler)

        rwAdapter.registerIsCheckedListener { isChecked, index ->
            presenter.itemChecked(isChecked, index)
        }
    }

    override fun updateAdapterList(list: List<ToDoItem>) {
        rwAdapter.updateData(list)
    }

    override fun showDialog() {
        val listener: (String) -> Unit = {
            presenter.onDialogClosed(it)
        }
        val dialog = ToDoDialog.Builder(this)
            .registerListener(listener)
            .build()
        dialog.show()
    }
}
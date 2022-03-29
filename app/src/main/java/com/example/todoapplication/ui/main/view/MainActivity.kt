package com.example.todoapplication.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.ui.main.adaptor.TodoAdaptor
import com.example.todoapplication.ui.main.viewmodel.MainViewModel
import com.example.todoapplication.ui.main.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var todoList: List<Todo>
    private val adaptor = TodoAdaptor(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val rvView = findViewById<RecyclerView>(R.id.rvView)
        rvView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory())[MainViewModel::class.java]

        viewModel.getTodos()
        //viewModel.todosList.value = emptyList()

        viewModel.todosList.observe(this) {

            adaptor.todos = it
            rvView.adapter = adaptor

        }


    }

}






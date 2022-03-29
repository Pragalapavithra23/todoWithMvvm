package com.example.todoapplication.ui.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.data.repository.MainRepository
import kotlinx.coroutines.launch


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


     private val _todosList = MutableLiveData<List<Todo>>()
     val todosList : LiveData<List<Todo>>
     get() = _todosList


     fun getTodos() = viewModelScope.launch {
       _todosList.value =mainRepository.getTodos()
    }

}
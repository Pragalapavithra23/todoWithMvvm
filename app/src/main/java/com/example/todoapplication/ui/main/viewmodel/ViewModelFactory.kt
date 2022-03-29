package com.example.todoapplication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplication.data.api.RetrofitInstance
import com.example.todoapplication.data.repository.MainRepository

 class ViewModelFactory :ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(MainRepository(RetrofitInstance.makeRetroFitService())) as T
    }
}
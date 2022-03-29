package com.example.todoapplication.data.repository

import com.example.todoapplication.data.api.RetrofitInstance
import com.example.todoapplication.data.api.TodoApiServiceInterface
import com.example.todoapplication.data.model.Todo
import retrofit2.Response

open class MainRepository(val service : TodoApiServiceInterface) {
 //  val service = RetrofitInstance.makeRetroFitService()
      suspend fun getTodos(): List<Todo> {
        return service.getTodos()?: emptyList()

    }
}
package com.example.todoapplication.data.api

import com.example.todoapplication.data.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApiServiceInterface {

     @GET("/v2/5a8e5b372f000048004f25fc")
      suspend fun getTodos() : List<Todo>
}
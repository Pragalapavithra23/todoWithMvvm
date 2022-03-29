package com.example.todoapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    const val BASE_URL ="http://www.mocky.io"


    fun makeRetroFitService() : TodoApiServiceInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TodoApiServiceInterface::class.java)
    }
}
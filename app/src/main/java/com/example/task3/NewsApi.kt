package com.example.task3

import com.example.task3.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {
    @GET("everything")
    suspend fun getEverything(@QueryMap map: Map<String, String>): Response<News>
}
package com.example.task3.data.data_source.remote.api

import com.example.task3.data.data_source.remote.dto.news.NewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApi {
    @GET("everything")
    suspend fun getEverything(@QueryMap map: Map<String, String>): Response<NewsDTO>

    @GET("top-headlines")
    suspend fun getHeadlines(@Query("language") lang: String): Response<NewsDTO>
}
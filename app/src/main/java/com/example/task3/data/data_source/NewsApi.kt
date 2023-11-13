package com.example.task3.data.data_source

import com.example.task3.data.data_source.dto.NewsDTO.NewsDTO
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {
    @GET("everything")
    suspend fun getEverything(@QueryMap map: Map<String, String>): NewsDTO
}
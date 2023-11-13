package com.example.task3

import com.example.task3.RetrofitService.instance
import com.example.task3.model.News
import retrofit2.Response

class MainRepository {

    private val api: NewsApi by lazy {
        instance.create(NewsApi :: class.java)
    }

    suspend fun getEverything(map: Map<String, String>): Response<News> = api.getEverything(map)
}
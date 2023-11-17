package com.example.task3.domain.repository

import com.example.task3.data.data_source.remote.dto.news.NewsDTO
import retrofit2.Response

interface NewsRemoteRepository {
    suspend fun getEverything(map: Map<String, String>): Response<NewsDTO>

    suspend fun getHeadlines(map: Map<String, String>): Response<NewsDTO>
}
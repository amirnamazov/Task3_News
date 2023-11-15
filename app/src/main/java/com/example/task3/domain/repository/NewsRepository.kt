package com.example.task3.domain.repository

import com.example.task3.data.data_source.remote.dto.NewsDTO.NewsDTO
import retrofit2.Response

interface NewsRepository {
    suspend fun getEverything(map: Map<String, String>): Response<NewsDTO>
}
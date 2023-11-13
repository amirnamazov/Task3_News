package com.example.task3.domain.repository

import com.example.task3.data.data_source.dto.NewsDTO.NewsDTO

interface NewsRepository {
    suspend fun getEverything(map: Map<String, String>): NewsDTO
}
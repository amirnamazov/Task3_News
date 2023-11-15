package com.example.task3.data.data_source.remote.dto.NewsDTO

data class NewsDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
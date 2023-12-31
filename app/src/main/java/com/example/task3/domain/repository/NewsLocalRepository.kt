package com.example.task3.domain.repository

import com.example.task3.data.data_source.local.model.ArticleModel

interface NewsLocalRepository {

    suspend fun allArticles(): List<ArticleModel>

    suspend fun insertArticle(articleModel: ArticleModel)

    suspend fun deleteArticle(id: Int)

    suspend fun deleteLastArticle()
}
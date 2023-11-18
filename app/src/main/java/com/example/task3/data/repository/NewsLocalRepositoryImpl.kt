package com.example.task3.data.repository

import com.example.task3.data.data_source.local.dao.ArticleDao
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.repository.NewsLocalRepository
import javax.inject.Inject

class NewsLocalRepositoryImpl @Inject constructor(private val dao: ArticleDao) : NewsLocalRepository {

    override suspend fun allArticles() = dao.getAll()

    override suspend fun insertArticle(articleModel: ArticleModel) = dao.insert(articleModel)

    override suspend fun deleteArticle(id: Int) = dao.deleteById(id)

    override suspend fun deleteLastArticle() = dao.deleteLast()
}
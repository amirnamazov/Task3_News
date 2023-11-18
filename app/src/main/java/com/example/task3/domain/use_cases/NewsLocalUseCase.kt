package com.example.task3.domain.use_cases

import com.example.task3.common.LocalDBService.flowResponse
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.repository.NewsLocalRepository
import javax.inject.Inject

class NewsLocalUseCase @Inject constructor(private val repository: NewsLocalRepository) {

    fun getAll() = flowResponse { repository.allArticles() }

    fun insert(model: ArticleModel) = flowResponse { repository.insertArticle(model) }

    fun delete(id: Int) = flowResponse { repository.deleteArticle(id) }

    fun deleteLast() = flowResponse { repository.deleteLastArticle() }
}
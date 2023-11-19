package com.example.task3.domain.use_cases

import com.example.task3.common.IOService.flowData
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.repository.NewsLocalRepository
import javax.inject.Inject

class NewsLocalUseCase @Inject constructor(private val repository: NewsLocalRepository) {

    fun getAll() = flowData { repository.allArticles() }

    fun insert(model: ArticleModel) = flowData { repository.insertArticle(model) }

    fun delete(id: Int) = flowData { repository.deleteArticle(id) }

    fun deleteLast() = flowData { repository.deleteLastArticle() }
}
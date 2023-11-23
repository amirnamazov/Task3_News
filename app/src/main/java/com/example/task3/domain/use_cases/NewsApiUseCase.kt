package com.example.task3.domain.use_cases

import com.example.task3.common.IOService.flowResponse
import com.example.task3.domain.repository.NewsRemoteRepository
import javax.inject.Inject

class NewsApiUseCase @Inject constructor(private val repository: NewsRemoteRepository) {

    fun getEverything(map: Map<String, String>) = flowResponse { repository.getEverything(map) }

    fun getHeadlines() = flowResponse { repository.getHeadlines() }

    fun getLangValue(): String = repository.getLangValue()

    fun setLangValue(value: String) = repository.setLangValue(value)
}
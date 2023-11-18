package com.example.task3.presentation.details

import com.example.task3.data.data_source.local.model.ArticleModel

sealed interface DetailsState {
    data class Success(
        val data: List<ArticleModel>? = null,
        val message: String = "Successfully done!"
    ) : DetailsState
    data class Error(val message: String) : DetailsState
}
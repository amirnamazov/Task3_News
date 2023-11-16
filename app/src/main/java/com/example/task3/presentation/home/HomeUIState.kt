package com.example.task3.presentation.home

import com.example.task3.data.data_source.remote.dto.news.Article

sealed class HomeUIState {
    data object Loading : HomeUIState()
    data class Success(val data: List<Article>) : HomeUIState()
    data class Error(val message: String) : HomeUIState()
    data object ConnectionError : HomeUIState()
    data object Empty : HomeUIState()
}
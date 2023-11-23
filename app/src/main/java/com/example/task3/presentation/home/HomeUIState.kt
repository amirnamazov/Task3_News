package com.example.task3.presentation.home

import com.example.task3.domain.model.Article

sealed class HomeUIState(val message: String? = null) {
    data object Loading : HomeUIState()
    data class Success(val data: List<Article>) : HomeUIState()
    class Error(message: String) : HomeUIState(message)
    class ConnectionError(message: String) : HomeUIState(message)
    data object Empty : HomeUIState()
}
package com.example.task3.presentation.home

import com.example.task3.domain.model.Article

sealed interface HomeUIState {
    data object Loading : HomeUIState
    data class Success(val data: List<Article>) : HomeUIState
    data class Error(val message: String) : HomeUIState
    data class ConnectionError(val message: String) : HomeUIState
    data object Empty : HomeUIState
}
package com.example.task3.presentation.saved

import com.example.task3.data.data_source.local.model.ArticleModel

sealed interface SavedUIState {
    data object Loading: SavedUIState
    data class Success(
        val data: List<ArticleModel>?,
        val message: String = "Successfully done!"
    ) : SavedUIState
    data class Error(val message: String) : SavedUIState
    data object Empty: SavedUIState
}
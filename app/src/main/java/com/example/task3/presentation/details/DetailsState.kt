package com.example.task3.presentation.details

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val message: String = "Successfully done!") : DetailsState()
    data class Error(val message: String) : DetailsState()
}
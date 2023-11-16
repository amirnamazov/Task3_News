package com.example.task3.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.domain.use_cases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsUseCase) : ViewModel() {

    private val _resHeadlines = MutableStateFlow<HomeUIState>(HomeUIState.Empty)
    val resHeadlines: StateFlow<HomeUIState> get() = _resHeadlines

    private val _resEverything = MutableStateFlow<HomeUIState>(HomeUIState.Empty)
    val resEverything: StateFlow<HomeUIState> get() = _resEverything

    fun fetchHeadlines(map: Map<String, String>) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getHeadlines(map).collect { res ->
            _resHeadlines.value = when(res) {
                is ResourceState.ConnectionError -> HomeUIState.ConnectionError
                is ResourceState.Error -> HomeUIState.Error(res.message!!)
                is ResourceState.Loading -> HomeUIState.Loading
                is ResourceState.Success -> HomeUIState.Success(res.data!!.articles)
            }
        }
    }

    fun fetchEverything(map: Map<String, String>) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getEverything(map).collect { res ->
            _resEverything.value = when(res) {
                is ResourceState.ConnectionError -> HomeUIState.ConnectionError
                is ResourceState.Error -> HomeUIState.Error(res.message!!)
                is ResourceState.Loading -> HomeUIState.Loading
                is ResourceState.Success -> HomeUIState.Success(res.data!!.articles)
            }
        }
    }
}
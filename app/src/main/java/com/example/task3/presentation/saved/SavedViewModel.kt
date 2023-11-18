package com.example.task3.presentation.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import com.example.task3.domain.use_cases.NewsLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val useCase: NewsLocalUseCase) : ViewModel() {

    private val _articleList = MutableLiveData<SavedUIState>()
    val articleList: LiveData<SavedUIState> get() = _articleList

    fun getArticleList() = viewModelScope.launch {
        useCase.getAll().collect { res ->
            _articleList.value = when (res) {
                is ResourceState.ConnectionError -> SavedUIState.Error(res.message!!)
                is ResourceState.Error -> SavedUIState.Error(res.message!!)
                is ResourceState.Loading -> SavedUIState.Loading
                is ResourceState.Success ->
                    if (res.data.isNullOrEmpty()) SavedUIState.Empty
                    else SavedUIState.Success(res.data)
            }
        }
    }
}
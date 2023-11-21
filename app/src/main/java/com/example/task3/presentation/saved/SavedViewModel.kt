package com.example.task3.presentation.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.task3.common.ResourceState
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.domain.use_cases.NewsLocalUseCase
import com.example.task3.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val useCase: NewsLocalUseCase) : BaseViewModel() {

    private val _articleList = MutableLiveData<SavedUIState>()
    val articleList: LiveData<SavedUIState> get() = _articleList

    fun getArticleList() = useCase.getAll().fetchData(_articleList) { setUIState(it) }

    private fun setUIState(res: ResourceState<List<ArticleModel>>): SavedUIState = when (res) {
        is ResourceState.ConnectionError -> SavedUIState.Error(res.message!!)
        is ResourceState.Error -> SavedUIState.Error(res.message!!)
        is ResourceState.Loading -> SavedUIState.Loading
        is ResourceState.Success ->
            if (res.data.isNullOrEmpty()) SavedUIState.Empty
            else SavedUIState.Success(res.data)
    }
}
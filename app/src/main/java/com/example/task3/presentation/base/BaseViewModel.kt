package com.example.task3.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task3.common.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    protected fun <Model, State> Flow<ResourceState<Model>>.fetchData(
        livedata: MutableLiveData<State>,
        state: (ResourceState<Model>) -> State
    ) = viewModelScope.launch(Dispatchers.IO) {
        this@fetchData.collect { res ->
            withContext(Dispatchers.Main) {
                livedata.value = state(res)
            }
        }
    }
}
package com.example.task3.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.task3.data.data_source.remote.dto.news.Article
import com.example.task3.databinding.FragmentHomeBinding
import com.example.task3.databinding.ItemHeadlineBinding
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.base.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch {
            val mapHeadline = mapOf("country" to "us")
            viewModel.fetchHeadlines(mapHeadline).join()

            val mapEverything = mapOf("q" to "tesla")
            viewModel.fetchEverything(mapEverything)
        }

        lifecycleScope.launch {

            launch {
                viewModel.resHeadlines.collect { state ->
                    binding.tv.text = when(state) {
                        is HomeUIState.ConnectionError -> "network error"
                        is HomeUIState.Error -> state.message
                        is HomeUIState.Loading -> "loading"
                        is HomeUIState.Success -> setupRvHeadlines(state.data).toString()
                        is HomeUIState.Empty -> ""
                    }
                }
            }

            launch {
                viewModel.resEverything.collect { state ->
                    binding.tv2.text = when(state) {
                        is HomeUIState.ConnectionError -> "network error"
                        is HomeUIState.Error -> state.message
                        is HomeUIState.Loading -> "loading"
                        is HomeUIState.Success -> state.data[0].description
                        is HomeUIState.Empty -> ""
                    }
                }
            }
        }
    }

    private fun setupRvHeadlines(list: List<Article>) = binding.rvHeadlines.apply {
        adapter = CustomAdapter(ItemHeadlineBinding::inflate, list.size) { b, i ->
            b.article = list[i]
        }
    }
}
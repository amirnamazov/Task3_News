package com.example.task3.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.task3.data.data_source.remote.dto.news.Article
import com.example.task3.databinding.FragmentHomeBinding
import com.example.task3.databinding.ItemHeadlineBinding
import com.example.task3.databinding.ItemNewsBinding
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.utils.CustomAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        observeHeadlines()
//        observeNews()
//
//        val mapHeadline = mapOf("country" to "us")
//        viewModel.fetchHeadlines(mapHeadline)
//
//        val mapEverything = mapOf("q" to "tesla")
//        viewModel.fetchNews(mapEverything)
    }

    private fun observeHeadlines() = viewModel.resHeadlines.observe(viewLifecycleOwner) { state ->
        when (state) {
            is HomeUIState.ConnectionError -> {}
            is HomeUIState.Error -> {}
            is HomeUIState.Loading -> setupRvHeadlines()
            is HomeUIState.Success -> setupRvHeadlines(state.data)
            is HomeUIState.Empty -> {}
        }
    }

    private fun observeNews() = viewModel.resNews.observe(viewLifecycleOwner) { state ->
        when (state) {
            is HomeUIState.ConnectionError -> {}
            is HomeUIState.Error -> {}
            is HomeUIState.Loading -> setupRvNews()
            is HomeUIState.Success -> setupRvNews(state.data)
            is HomeUIState.Empty -> {}
        }
    }

    private fun setupRvHeadlines(list: List<Article>? = null) = binding.rvHeadlines.apply {
        list?.let { binding.slHeadlines.cancelShimmer() }
        adapter = CustomAdapter(ItemHeadlineBinding::inflate, list?.size ?: 10) { b, i ->
            list?.let {
                b.article = it[i]
            }
        }
    }

    private fun setupRvNews(list: List<Article>? = null) = binding.rvNews.apply {
        list?.let { binding.slNews.cancelShimmer() }
        adapter = CustomAdapter(ItemNewsBinding::inflate, list?.size ?: 10) { b, i ->
            list?.let {
                b.article = it[i]
            }
        }
    }

    private fun ShimmerFrameLayout.cancelShimmer() {
        if (isShimmerStarted) {
            stopShimmer()
            hideShimmer()
        }
    }
}
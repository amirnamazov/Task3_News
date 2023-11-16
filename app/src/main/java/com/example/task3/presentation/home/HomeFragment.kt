package com.example.task3.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.task3.data.data_source.remote.dto.news.Article
import com.example.task3.databinding.FragmentHomeBinding
import com.example.task3.databinding.ItemHeadlineBinding
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.utils.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.resHeadlines.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeUIState.ConnectionError -> {}
                is HomeUIState.Error -> {}
                is HomeUIState.Loading -> setupRvHeadlines()
                is HomeUIState.Success -> setupRvHeadlines(state.data)
                is HomeUIState.Empty -> {}
            }
        }

        val mapHeadline = mapOf("country" to "us")
        viewModel.fetchHeadlines(mapHeadline)

//        val mapEverything = mapOf("q" to "tesla")
//        viewModel.fetchEverything(mapEverything)
    }

    private fun setupRvHeadlines(list: List<Article>? = null) = binding.rvHeadlines.apply {
        adapter = CustomAdapter(ItemHeadlineBinding::inflate, list?.size ?: 10) { b, i ->
            list?.let {
                b.article = it[i]
                if (binding.shimmerLayout.isShimmerStarted) {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.hideShimmer()
                    println("dkbdljbvdfuvdfuo")
                }
            }
        }
    }
}
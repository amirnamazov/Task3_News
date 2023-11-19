package com.example.task3.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.databinding.FragmentHomeBinding
import com.example.task3.databinding.ItemHeadlineBinding
import com.example.task3.databinding.ItemNewsBinding
import com.example.task3.domain.model.Article
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.details.DetailsActivity
import com.example.task3.presentation.utils.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.etSearch.doAfterTextChanged { text ->
            if (!text.isNullOrEmpty()) viewModel.fetchNews(text.toString())
        }

//        viewModel.fetchHeadlines()
//        observeHeadlines()

//        viewModel.fetchNews()
//        observeNews()
    }

    private fun observeHeadlines() = viewModel.resHeadlines.observe(viewLifecycleOwner) { state ->
        when (state) {
            is HomeUIState.Loading -> setupRvHeadlines(count = 10)
            is HomeUIState.Success -> setupRvHeadlines(state.data)
            else -> setupRvHeadlines()
        }
    }

    private fun observeNews() = viewModel.resNews.observe(viewLifecycleOwner) { state ->
        when (state) {
            is HomeUIState.Loading -> setupRvNews(count = 10)
            is HomeUIState.Success -> setupRvNews(state.data)
            else -> setupRvNews()
        }
    }

    private fun setupRvHeadlines(list: List<Article>? = null, count: Int = 0) {
        list?.let { binding.slHeadlines.hideShimmer() }
        binding.rvHeadlines.adapter =
            CustomAdapter(ItemHeadlineBinding::inflate, list?.size ?: count) { b, i ->
                list?.let {
                    b.article = it[i]
                    b.onItemClickListener(it[i])
                }
            }
    }

    private fun setupRvNews(list: List<Article>? = null, count: Int = 0) = binding.rvNews.apply {
        list?.let { binding.slNews.hideShimmer() }
        adapter = CustomAdapter(ItemNewsBinding::inflate, list?.size ?: count) { b, i ->
            list?.let {
                b.article = it[i]
                b.onItemClickListener(it[i])
            }
        }
    }

    private fun ViewBinding.onItemClickListener(article: Article) = root.setOnClickListener {
        startActivity(
            Intent(requireContext(), DetailsActivity :: class.java).apply {
                putExtra("ARTICLE_MODEL", ArticleModel(article))
            }
        )
    }
}
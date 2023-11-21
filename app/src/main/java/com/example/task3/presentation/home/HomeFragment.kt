package com.example.task3.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.databinding.FragmentHomeBinding
import com.example.task3.databinding.ItemHeadlineBinding
import com.example.task3.databinding.ItemNewsBinding
import com.example.task3.domain.model.Article
import com.example.task3.presentation.MainActivity
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.details.DetailsActivity
import com.example.task3.presentation.utils.CustomAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private var searchText by Delegates.observable("") { _, old, new ->
        if (new != old && new.isNotEmpty()) viewModel.searchNews(new)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.etSearch.doAfterTextChanged { searchText = it.toString() }
        setupLanguageButton()

        viewModel.fetchHeadlines()
        viewModel.resHeadlines.observe(binding.slHeadlines) { list, count ->
            binding.rvHeadlines.setAdapter(ItemHeadlineBinding::inflate, list, count)
        }

        viewModel.fetchNews()
        viewModel.resNews.observe(binding.slNews) { list, count ->
            binding.rvNews.setAdapter(ItemNewsBinding::inflate, list, count)
        }
    }

    private fun setupLanguageButton() = binding.btnLang.apply {
        text = viewModel.sharedPref.getString("LANGUAGE", "en")?.uppercase()
        val dialogLang = BottomSheetDialogLang(requireContext(), viewModel.sharedPref) {
            viewModel.fetchHeadlines()
            viewModel.fetchNews()
            text = it
        }
        setOnClickListener { dialogLang.show() }
    }

    private fun LiveData<HomeUIState>
            .observe(sfl: ShimmerFrameLayout, setAdapter: (List<Article>?, Int) -> Unit) =
        observe(viewLifecycleOwner) { state ->
            when(state) {
                HomeUIState.Loading -> sfl.start()
                else -> sfl.stop()
            }
            when (state) {
                is HomeUIState.Loading -> setAdapter(null, 10)
                is HomeUIState.Success -> setAdapter(state.data, state.data.size)
                is HomeUIState.ConnectionError -> {
                    (requireActivity() as MainActivity).showDialog(state.message)
                    setAdapter(null, 0)
                }
                is HomeUIState.Error -> {
                    (requireActivity() as MainActivity).showDialog(state.message)
                    setAdapter(null, 0)
                }
                is HomeUIState.Empty -> setAdapter(null, 0)
            }
        }

    private fun RecyclerView.setAdapter(
        inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
        list: List<Article>?, count: Int
    ) {
        adapter = CustomAdapter(inflate, list?.size ?: count) { b, i ->
            if (!list.isNullOrEmpty()) {
                if (b is ItemHeadlineBinding) b.article = list[i]
                if (b is ItemNewsBinding) b.article = list[i]
                b.onItemClickListener(list[i])
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

    private fun ShimmerFrameLayout.stop() {
        stopShimmer()
        hideShimmer()
    }

    private fun ShimmerFrameLayout.start() {
        startShimmer()
        showShimmer(true)
    }
}
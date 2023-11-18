package com.example.task3.presentation.saved

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.databinding.FragmentSavedBinding
import com.example.task3.databinding.ItemNewsBinding
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.details.DetailsActivity
import com.example.task3.presentation.utils.CustomAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : BaseFragment<FragmentSavedBinding>(FragmentSavedBinding :: inflate) {

    private val viewModel: SavedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeNews()
        viewModel.getArticleList()
    }

    private fun observeNews() = viewModel.articleList.observe(viewLifecycleOwner) { state ->
        when (state) {
            is SavedUIState.Loading -> setupRvNews()
            is SavedUIState.Success -> setupRvNews(state.data)
            is SavedUIState.Error -> {}
            is SavedUIState.Empty -> {}
        }
    }

    private fun setupRvNews(list: List<ArticleModel>? = null) = binding.rvSavedNews.apply {
        list?.let { binding.slSavedNews.cancelShimmer() }
        adapter = CustomAdapter(ItemNewsBinding::inflate, list?.size ?: 10) { b, i ->
            list?.let {
                b.article = it[i].article
                b.onItemClickListener(it[i])
            }
        }
    }

    private fun ViewBinding.onItemClickListener(model: ArticleModel) = root.setOnClickListener {
        startActivity(
            Intent(requireContext(), DetailsActivity :: class.java).apply {
                putExtra("ARTICLE_MODEL", model)
            }
        )
    }

    private fun ShimmerFrameLayout.cancelShimmer() {
        if (isShimmerStarted) {
            stopShimmer()
            hideShimmer()
        }
    }
}
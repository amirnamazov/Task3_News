package com.example.task3.presentation.saved

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.databinding.FragmentSavedBinding
import com.example.task3.databinding.ItemNewsBinding
import com.example.task3.presentation.base.BaseFragment
import com.example.task3.presentation.details.DetailsActivity
import com.example.task3.presentation.utils.CustomAdapter
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
            is SavedUIState.Loading -> setupRvNews(count = 10)
            is SavedUIState.Success -> setupRvNews(state.data)
            else -> setupRvNews()
        }
    }

    private fun setupRvNews(list: List<ArticleModel>? = null, count: Int = 0) {
        list?.let { binding.slSavedNews.hideShimmer() }
        binding.rvSavedNews.adapter =
            CustomAdapter(ItemNewsBinding::inflate, list?.size ?: count) { b, i ->
                list?.let {
                    b.article = it[i].article
                    b.onItemClickListener(it[i])
                }
            }
    }

    private fun ViewBinding.onItemClickListener(model: ArticleModel) = root.setOnClickListener {
        val intent = Intent(requireContext(), DetailsActivity :: class.java).apply {
            putExtra("ARTICLE_MODEL", model)
        }
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) viewModel.getArticleList()
    }
}
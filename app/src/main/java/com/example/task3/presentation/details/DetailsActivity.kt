package com.example.task3.presentation.details

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.task3.R
import com.example.task3.databinding.ActivityDetailsBinding
import com.example.task3.domain.model.Article
import com.example.task3.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>(ActivityDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    private val article by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("ARTICLE", Article::class.java)
        } else {
            intent.getParcelableExtra("ARTICLE")
        }
    }

    private var saved: Boolean = false

    override fun initialize() {
        setupActionBar()

        saved = intent.getBooleanExtra("ARTICLE_SAVED", false)

        initViews()
        observeArticleList()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null
    }

    private fun initViews() = article?.let {
        binding.article = it
        it.urlToImage?.let { url ->
            Glide.with(this@DetailsActivity).load(url).into(binding.image)
        }
    }

    private fun observeArticleList() = viewModel.articleList.observe(this) { state ->
        when (state) {
            is DetailsState.Error -> println("iudfgifdugdfiug errrror  ${state.message}")
            is DetailsState.Success -> println("iudfgifdugdfiug  ${state.message}   ${state.data!!.map { it.article.author }}")
        }
    }

    private fun LiveData<DetailsState>.observe(save: Boolean, onSuccess: () -> Unit) =
        observe(this@DetailsActivity) { state ->
            when (state) {
                is DetailsState.Error -> println("iudfgifdugdfiug errrror  ${state.message}")
                is DetailsState.Success -> {
                    println("iudfgifdugdfiug $save  ${state.message}")
                    saved = save; onSuccess()
                }
            }
        }

    private fun getSaveIcon(): Drawable? =
        if (saved) ContextCompat.getDrawable(this, R.drawable.ic_saved)
        else ContextCompat.getDrawable(this, R.drawable.ic_save)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> article?.let {
                if (saved) viewModel.removeLastArticle()
                else viewModel.saveArticle(it)
            }

            R.id.menu_share -> viewModel.getArticleList()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu!!.findItem(R.id.menu_save)
        item.icon = getSaveIcon()
        viewModel.articleSave.observe(true) { item.icon = getSaveIcon() }
        viewModel.articleRemove.observe(false) { item.icon = getSaveIcon() }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
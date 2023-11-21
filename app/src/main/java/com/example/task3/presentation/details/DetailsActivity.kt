package com.example.task3.presentation.details

import android.app.Activity
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.example.task3.R
import com.example.task3.data.data_source.local.model.ArticleModel
import com.example.task3.databinding.ActivityDetailsBinding
import com.example.task3.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>(ActivityDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()

    private val articleModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("ARTICLE_MODEL", ArticleModel::class.java)
        } else {
            intent.getParcelableExtra("ARTICLE_MODEL")
        }
    }

    private var saved by Delegates.notNull<Boolean>()

    override fun initialize() {
        setupActionBar()
        initViews()

        runBlocking {
            viewModel.checkExistenceInDb(articleModel!!) { saved ->
                this@DetailsActivity.saved = saved
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null
    }

    private fun initViews() = articleModel?.article.let {
        binding.article = it
        it?.urlToImage?.let { url ->
            Glide.with(this@DetailsActivity).load(url).into(binding.image)
        }
    }

    private fun LiveData<DetailsState>.observe(save: Boolean, onSuccess: () -> Unit) =
        observe(this@DetailsActivity) { state ->
            if (!save) setResult(Activity.RESULT_OK)
            else setResult(Activity.RESULT_CANCELED)
            when (state) {
                is DetailsState.Error -> binding.root.showSnackBar(state.message)
                is DetailsState.Success -> { saved = save; onSuccess() }
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> articleModel?.let {
                if (saved) viewModel.removeArticle(it.id)
                else viewModel.saveArticle(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val item = menu!!.findItem(R.id.menu_save)
        val savedIcon = ContextCompat.getDrawable(this, R.drawable.ic_saved)
        val saveIcon = ContextCompat.getDrawable(this, R.drawable.ic_save)
        if (saved) item.icon = savedIcon

        viewModel.articleSave.observe(true) { item.icon = savedIcon }
        viewModel.articleRemove.observe(false) { item.icon = saveIcon }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
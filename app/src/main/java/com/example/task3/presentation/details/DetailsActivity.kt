package com.example.task3.presentation.details

import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
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
            intent.getParcelableExtra("ARTICLE", Article :: class.java)
        } else {
            intent.getParcelableExtra("ARTICLE",)
        }
    }

    override fun initialize() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = null

        article?.let {
            binding.article = it
            it.urlToImage?.let { url ->
                binding.image.setImage(url)
            }
        }

        observeSaveData()
    }

    private fun observeSaveData() = viewModel.articleSave.observe(this) { state ->
        when (state) {
            is DetailsState.Error -> println("uvycutxutx errrror  ${state.message}")
            is DetailsState.Loading -> println("uvycutxutx  loading")
            is DetailsState.Success -> println("uvycutxutx  ${state.message}")
        }
    }


//    private fun collectSaveData() = lifecycleScope.launch {
//        viewModel.articleSave.collect { state ->
//            when (state) {
//                is DetailsState.Error -> Toast.makeText(this@DetailsActivity, state.message, Toast.LENGTH_SHORT).show()
//                is DetailsState.Loading -> Toast.makeText(this@DetailsActivity, "loading", Toast.LENGTH_SHORT).show()
//                is DetailsState.Success -> Toast.makeText(this@DetailsActivity, state.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun AppCompatImageView.setImage(imageUrl: String) = Glide
        .with(this@DetailsActivity)
        .load(imageUrl)
        .into(this)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_save -> {
                article?.let { viewModel.saveArticle(it) }
                true
            }
            R.id.menu_share -> {
                Toast.makeText(this, "dslbdfkjf", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
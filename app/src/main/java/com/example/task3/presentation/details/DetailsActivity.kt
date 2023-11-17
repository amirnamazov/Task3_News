package com.example.task3.presentation.details

import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.task3.R
import com.example.task3.databinding.ActivityDetailsBinding
import com.example.task3.domain.model.Article
import com.example.task3.presentation.base.BaseActivity

class DetailsActivity : BaseActivity<ActivityDetailsBinding>(ActivityDetailsBinding::inflate) {

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
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
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
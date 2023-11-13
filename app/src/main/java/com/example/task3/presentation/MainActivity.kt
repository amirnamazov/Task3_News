package com.example.task3.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.responseNews.observe(this) { res ->
            binding.textView.text = res
        }

        val map = mutableMapOf("q" to "tesla", "from" to "2023-10-12", "sortBy" to "publishedAt")

        viewModel.fetchNews(map)


//        lifecycleScope.launch(handler) {
//            val job = launch {
//                delay(3000L)
//                throw Exception("8798798797")
//            }
//            delay(500L)
//            job.cancel()
//        }

    }
}
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

//        binding.switcher.setOnCheckedChangeListener { buttonView, isChecked ->
//            runBlocking {
//                binding.textView.text = Thread.currentThread().name
//                delay(5000)
//            }
//        }

        viewModel.responseNews.observe(this@MainActivity) { res ->
            binding.textView.text = res
        }

        val map = mutableMapOf("q" to "tesla")

        viewModel.fetchNews(map)
    }
}
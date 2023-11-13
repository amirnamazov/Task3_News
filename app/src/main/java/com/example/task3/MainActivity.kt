package com.example.task3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.task3.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val map = mapOf("q" to "tesla", "from" to "2023-10-12", "sortBy" to "publishedAt")

        runBlocking {
            val response = async { viewModel.fetchNews(map) }.await()
            response.collect {
                println("dlsbfodiodifv   ${it.body()}")
            }
        }

//        val handler = CoroutineExceptionHandler { _, throwable ->
//            println("sdklnsdlkn ${throwable.message}")
//        }
//
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
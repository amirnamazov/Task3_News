package com.example.task3.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(
    private val inflate: (LayoutInflater) -> T
) : AppCompatActivity() {

    private var _binding: T? = null

    protected val binding: T get() = _binding!!

    abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding?.root)
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
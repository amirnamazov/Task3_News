package com.example.task3.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T : ViewBinding>(
    private val inflate: (LayoutInflater) -> T
) : AppCompatActivity() {

    protected val binding: T by lazy { inflate(layoutInflater) }

    private val dialog by lazy {
        MaterialAlertDialogBuilder(this)
            .setTitle("Message")
            .create()
    }

    abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
    }

    fun showDialog(message: String) = dialog.apply { setMessage(message) }.show()

    fun View.showSnackBar(message: String) =
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}
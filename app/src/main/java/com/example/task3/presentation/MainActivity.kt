package com.example.task3.presentation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.task3.databinding.ActivityMainBinding
import com.example.task3.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding :: inflate) {

    override fun initialize() {
        setupBottomNav()
    }

    private fun setupBottomNav() = binding.apply {
        val navHostFragment = supportFragmentManager.findFragmentById(fragContainer.id)
                as NavHostFragment
        bottomNav.setupWithNavController(navHostFragment.navController)
    }
}
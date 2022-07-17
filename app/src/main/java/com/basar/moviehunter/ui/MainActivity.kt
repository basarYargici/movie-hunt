package com.basar.moviehunter.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseActivity
import com.basar.moviehunter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun inflateLayout() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = findNavHostFragment(R.id.nav_host_fragment)
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun initViews(savedInstanceState: Bundle?) {}
}
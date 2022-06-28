package com.basar.moviehunter.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseActivity
import com.basar.moviehunter.databinding.ActivityMainBinding
import com.basar.moviehunter.util.Listener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), Listener {
    private lateinit var navController: NavController
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.upcomingFragment,
                R.id.downloadsFragment,
                R.id.othersFragment
            )
        )
    }

    override fun inflateLayout() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = findNavHostFragment(R.id.nav_host_fragment)
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun setListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    // TODO:  basar
                    false
                }
                R.id.searchFragment -> {
                    // TODO:  basar
                    false
                }
                R.id.upcomingFragment -> {
                    // TODO:  basar
                    false
                }
                R.id.downloadsFragment -> {
                    // TODO:  basar
                    false
                }
                R.id.othersFragment -> {
                    // TODO:  basar
                    false
                }
                else -> true
            }
        }
    }
}
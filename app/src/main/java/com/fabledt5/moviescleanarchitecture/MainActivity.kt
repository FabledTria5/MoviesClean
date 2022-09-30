package com.fabledt5.moviescleanarchitecture

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = getNavHost(R.id.fragmentContainerView)
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController = navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.searchFragment, R.id.profileFragment, R.id.filterFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }

                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    private fun getNavHost(resId: Int) =
        supportFragmentManager.findFragmentById(resId) as NavHostFragment

}
package com.fabledt5.moviescleanarchitecture.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fabledt5.moviescleanarchitecture.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    companion object {
        private const val SPLASH_SCREEN_DELAY = 700L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

}
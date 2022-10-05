package com.fabledt5.moviescleanarchitecture.presentation.adapters.pagers

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.MoviesFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.SelectionsFragment

class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf(MoviesFragment(), SelectionsFragment())

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]
}
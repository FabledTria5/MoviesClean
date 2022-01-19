package com.fabledt5.moviescleanarchitecture.presentation.adapters.pagers

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabledt5.moviescleanarchitecture.domain.util.MediaType
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.MoviesFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf(MoviesFragment.newInstance(MediaType.movie),
        MoviesFragment.newInstance(MediaType.tv))

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]
}
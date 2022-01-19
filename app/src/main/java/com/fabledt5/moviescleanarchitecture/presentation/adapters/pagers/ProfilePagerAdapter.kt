package com.fabledt5.moviescleanarchitecture.presentation.adapters.pagers

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfileMoviesFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfilePeopleFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProfilePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments =
        listOf(
            ProfileMoviesFragment.newInstance(MovieType.WANT),
            ProfileMoviesFragment.newInstance(MovieType.WATCHED),
            ProfilePeopleFragment()
        )

    override fun getItemCount() = fragments.count()

    override fun createFragment(position: Int) = fragments[position]

}
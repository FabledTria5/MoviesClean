package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentHomeBinding
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.presentation.adapters.pagers.HomePagerAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var viewmodelFactory: MultiViewModelFactory

    private val homeViewModel by viewModels<HomeViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { viewmodelFactory }
    )

    private val binding: FragmentHomeBinding by viewBinding()

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPager()
    }

    private fun initPager() {
        binding.homePager.adapter =
            HomePagerAdapter(fragmentManager = childFragmentManager, lifecycle = lifecycle)

        TabLayoutMediator(binding.homeTabs, binding.homePager) { tab, index ->
            when (index) {
                0 -> tab.text = getString(R.string.movies)
                1 -> tab.text = getString(R.string.tv_shows)
            }
        }.attach()
    }

}
package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentSelectionsBinding
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import javax.inject.Inject

class SelectionsFragment : Fragment(R.layout.fragment_selections) {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    private val homeViewModel by viewModels<HomeViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { viewModelFactory }
    )

    private val binding: FragmentSelectionsBinding by viewBinding()

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        startObserve()
    }

    private fun initUi() {

    }

    private fun startObserve() {

    }

}
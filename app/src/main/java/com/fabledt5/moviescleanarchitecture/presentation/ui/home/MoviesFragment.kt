package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentMoviesBinding
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.HomeMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    @Inject
    lateinit var viewmodelFactory: MultiViewModelFactory

    private val homeViewModel by viewModels<HomeViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { viewmodelFactory }
    )

    private val onContentClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            navigateToMoviePage(movieId, moviePoster)
        }
    }

    private val binding: FragmentMoviesBinding by viewBinding()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeMoviesListAdapter(onMovieClickListener = onContentClickListener)
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        startObserve()
    }

    private fun initUi() {
        binding.rvContentList.adapter = adapter
    }

    private fun startObserve() {
        homeViewModel.trendingMovies
            .onEach { result ->
                setResult(result)
            }.launchWhenStarted(lifecycleScope)
    }

    private fun setResult(result: Resource<List<MovieItem>>) {
        when (result) {
            is Resource.Error -> {
                setLoadingFinished()
                setHasError(true)
            }

            is Resource.Loading -> {
                setLoadingStarted()
                setHasError(false)
            }

            is Resource.Success -> {
                adapter.submitList(result.data)
                setLoadingFinished()
            }
        }
    }

    private fun setLoadingStarted() {
        binding.progressIndicator.isVisible = true
    }

    private fun setLoadingFinished() {
        binding.progressIndicator.isVisible = false
        binding.rvContentList.animateAlpha(targetAlpha = 1f)
    }

    private fun setHasError(hasError: Boolean) {
        binding.errorGroup.isVisible = hasError
    }

    private fun navigateToMoviePage(contentId: Int, contentPoster: String?) {
        HomeFragmentDirections.actionOpenMovie(contentId, contentPoster).also { direction ->
            findNavController().navigate(direction)
        }
    }
}
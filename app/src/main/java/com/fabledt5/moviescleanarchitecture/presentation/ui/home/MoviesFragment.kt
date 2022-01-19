package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentMoviesBinding
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.util.MediaType
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.HomeMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    companion object {
        private const val CONTENT_TYPE = "contentType"

        fun newInstance(contentType: MediaType) =
            MoviesFragment().arguments(CONTENT_TYPE to contentType.name)
    }

    @Inject
    lateinit var viewmodelFactory: MultiViewModelFactory

    private val homeViewModel by viewModels<HomeViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { viewmodelFactory }
    )

    private val onContentClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            when (contentType) {
                MediaType.movie -> navigateToMoviePage(movieId, moviePoster)
                MediaType.tv -> navigateToTvShowPage(movieId, moviePoster)
            }
        }
    }

    private val binding: FragmentMoviesBinding by viewBinding()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeMoviesListAdapter(onMovieClickListener = onContentClickListener)
    }

    private var contentType = MediaType.movie

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
        val contentSource = when (requireArguments().getString(CONTENT_TYPE)) {
            MediaType.movie.name -> homeViewModel.trendingMovies
            MediaType.tv.name -> {
                contentType = MediaType.tv
                homeViewModel.trendingShows
            }
            else -> {
                Timber.e(message = "Error with received arguments: ${
                    requireArguments().getString(CONTENT_TYPE)
                }")
                return
            }
        }

        contentSource.onEach { result ->
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
            else -> Unit
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

    private fun navigateToTvShowPage(
        contentId: Int,
        contentPoster: String?
    ) {
        // TODO: 02.11.2021
    }
}
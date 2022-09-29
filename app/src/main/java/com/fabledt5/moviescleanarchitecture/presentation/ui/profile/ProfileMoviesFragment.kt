package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentProfileMoviesBinding
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.ProfileMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.arguments
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class ProfileMoviesFragment : Fragment(R.layout.fragment_profile_movies) {

    companion object {
        private const val MOVIE_OBSERVE_DELAY = 200L
        private const val MOVIES_TYPE = "moviesType"

        fun newInstance(movieType: MovieType) =
            ProfileMoviesFragment().arguments(MOVIES_TYPE to movieType.name)
    }

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val profileViewModel by viewModels<ProfileViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private val binding: FragmentProfileMoviesBinding by viewBinding()

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            ProfileFragmentDirections.actionProfileFragmentToMovieFragment(movieId, moviePoster)
                .also { findNavController().navigate(it) }
        }
    }

    private val onDeleteMovieClickListener: (Int) -> Unit = { profileViewModel.deleteMovie(it) }

    private var profileMoviesListLayoutManager: GridLayoutManager? = null

    private val profileMoviesListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ProfileMoviesListAdapter(
            onMovieClickListener = onMovieClickListener,
            onDeleteMovieClickListener = onDeleteMovieClickListener,
            layoutManager = profileMoviesListLayoutManager
        )
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        observeListShape()
        Handler(Looper.getMainLooper()).postDelayed(::observeMovies, MOVIE_OBSERVE_DELAY)
    }

    private fun initUi() {
        profileMoviesListLayoutManager = GridLayoutManager(context, 1)

        binding.rvMoviesList.layoutManager = profileMoviesListLayoutManager
        binding.rvMoviesList.adapter = profileMoviesListAdapter
    }

    private fun observeMovies() {
        val contentSource = when (requireArguments().getString(MOVIES_TYPE)) {
            MovieType.WANT.name -> profileViewModel.wantedMovies
            MovieType.WATCHED.name -> profileViewModel.watchedMovies
            else -> throw IllegalArgumentException("Illegal movie type")
        }

        contentSource.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showEmptyList()
                    Timber.e(result.message)
                }

                is Resource.Success -> showMovies(result.data)
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun observeListShape() {
        profileViewModel.listsShape.onEach { shape ->
            profileMoviesListLayoutManager?.spanCount = shape.spanCount
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showMovies(movies: List<MovieItem>?) {
        binding.tvEmptyListMessage.visibility = View.INVISIBLE
        profileMoviesListAdapter.submitList(movies) {
            binding.rvMoviesList.animateAlpha(targetAlpha = 1f, duration = 800)
            binding.rvMoviesList.scrollToPosition(0)
        }
    }

    private fun showEmptyList() {
        profileMoviesListAdapter.submitList(listOf()) {
            binding.rvMoviesList.animateAlpha(targetAlpha = 0f, duration = 800)
            binding.tvEmptyListMessage.visibility = View.VISIBLE
        }
    }

}
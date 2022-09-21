package com.fabledt5.moviescleanarchitecture.presentation.ui.movie

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentMovieBinding
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.MovieCastListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.MovieGenresAdapter
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.SimilarMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.hide
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import com.fabledt5.moviescleanarchitecture.presentation.utils.setBackgroundTint
import com.fabledt5.moviescleanarchitecture.presentation.utils.setDrawableDivider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieFragment : Fragment(R.layout.fragment_movie) {

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val binding: FragmentMovieBinding by viewBinding()
    private val navArgs: MovieFragmentArgs by navArgs()

    private val movieViewModel by viewModels<MovieViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private val onSimilarMoviesClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            movieViewModel.changeMovie(movieId, moviePoster)
        }
    }

    private val onPersonClickListener = object : OnPersonClickListener {
        override fun onPersonClick(personId: Int, personImage: String?) {
            MovieFragmentDirections.actionMovieFragmentToPersonFragment(personId, personImage)
                .also { direction ->
                    findNavController().navigate(direction)
                }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            movieViewModel.getMovieFromBackStack()
            binding.movieDetailsBottomSheet.nestedScrollView.fullScroll(View.FOCUS_UP)
        }
    }

    private val movieTitleChangeListener =
        View.OnLayoutChangeListener { v, _, _, _, bottom, _, _, _, oldBottom ->
            val heightWas = oldBottom - bottom
            if (v.height != heightWas) {
                expandBottomSheet()
            }
        }

    private val genresAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieGenresAdapter()
    }

    private val movieCastAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieCastListAdapter(onPersonClickListener)
    }

    private val similarMoviesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SimilarMoviesListAdapter(onSimilarMoviesClickListener)
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
        observeMovieDetails()
        observeBackNavigation()

        movieViewModel.changeMovie(movieId = navArgs.movieId, moviePoster = navArgs.moviePoster)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieViewModel.onDetach()
    }

    private fun initUi() = with(binding.movieDetailsBottomSheet) {
        rvCast.adapter = movieCastAdapter
        rvCast.setDrawableDivider(R.drawable.lists_divider)

        rvGenres.adapter = genresAdapter
        rvGenres.setDrawableDivider(R.drawable.lists_divider)

        rvMoreLikeThis.adapter = similarMoviesAdapter
        rvMoreLikeThis.setDrawableDivider(R.drawable.lists_divider)
    }

    private fun setupListeners() = with(binding.movieDetailsBottomSheet) {
        btnWant.setOnClickListener { movieViewModel.addMovieToWanted() }
        btnWatched.setOnClickListener { movieViewModel.addMovieToWatched() }

        tvMovieOverview.addOnLayoutChangeListener(movieTitleChangeListener)
    }

    private fun observeBackNavigation() = movieViewModel.canNavigateBack.onEach { canNavigateBack ->
        onBackPressedCallback.isEnabled = canNavigateBack
    }.launchWhenStarted(lifecycleScope)

    private fun observeMovieDetails() {
        movieViewModel.moviePoster.onEach { poster ->
            binding.ivImagePoster.load(poster) {
                crossfade(enable = true)
                error(R.drawable.poster_placeholder)
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieDetails.onEach { result ->
            when (result) {
                is Resource.Success -> showResult(result.data)
                is Resource.Error -> showMovieLoadingError()
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieType.onEach { type ->
            when (type) {
                MovieType.WATCHED -> selectWatchButton()
                MovieType.WANT -> selectWantButton()
                MovieType.NONE -> clearButtons()
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieCast.onEach { result ->
            when (result) {
                is Resource.Success -> movieCastAdapter.submitList(result.data)
                is Resource.Error -> {
                    Timber.e(result.message)
                    binding.movieDetailsBottomSheet.materialTextView5.hide()
                    binding.movieDetailsBottomSheet.rvCast.hide()
                }

                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.similarMovies.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Timber.e(result.message)
                    binding.movieDetailsBottomSheet.materialTextView4.hide()
                    binding.movieDetailsBottomSheet.rvMoreLikeThis.hide()
                }

                is Resource.Success -> similarMoviesAdapter.submitList(result.data)
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieTrailer.onEach { result ->
            when (result) {
                is Resource.Error -> Timber.e(result.message)
                is Resource.Success -> enableTrailerButton(result.data)
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showResult(movie: MovieItem?) = with(binding.movieDetailsBottomSheet) {
        movie?.let {
            tvMovieName.text = it.movieTitle
            tvMovieReleaseYear.text = it.movieRelease
            tvMovieCountry.text = it.movieCountry
            tvMovieDuration.text = it.movieRuntime
            tvMovieRating.text = it.movieRating.toString()

            tvMovieOverview.text = it.movieOverview
            genresAdapter.setItems(it.movieGenres)
        }
        movieContentProgressBar.hide()
        topContentGroup.isVisible = true
    }

    private fun showMovieLoadingError() = with(binding.movieDetailsBottomSheet) {
        movieContentProgressBar.hide()
        topContentGroup.visibility = View.INVISIBLE
        movieLoadingErrorMessage.animateAlpha(duration = 200, targetAlpha = 1f)
    }

    private fun enableTrailerButton(url: String) = with(binding) {
        civPlayButton.animateAlpha(duration = 200, targetAlpha = 1f)
        civPlayButton.setOnClickListener {
            MovieFragmentDirections.actionMovieFragmentToTrailerFragment(url).also { direction ->
                findNavController().navigate(direction)
            }
        }
    }

    private fun expandBottomSheet() {
        BottomSheetBehavior.from(binding.movieDetailsBottomSheet.bottomSheet).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            peekHeight = calculateBottomSheetPeekHeight()
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun calculateBottomSheetPeekHeight() =
        binding.movieDetailsBottomSheet.tvMovieOverview.top

    private fun clearButtons() = with(binding.movieDetailsBottomSheet) {
        val tintList =
            ContextCompat.getColorStateList(requireContext(), R.color.unselected_button_tint_list)
        btnWant.backgroundTintList = tintList
        btnWatched.backgroundTintList = tintList
    }

    private fun selectWantButton() = with(binding.movieDetailsBottomSheet) {
        btnWatched.setBackgroundTint(requireContext(), R.color.unselected_button_tint_list)
        btnWant.setBackgroundTint(requireContext(), R.color.selected_button_tint_list)
    }

    private fun selectWatchButton() = with(binding.movieDetailsBottomSheet) {
        btnWant.setBackgroundTint(requireContext(), R.color.unselected_button_tint_list)
        btnWatched.setBackgroundTint(requireContext(), R.color.selected_button_tint_list)
    }

}

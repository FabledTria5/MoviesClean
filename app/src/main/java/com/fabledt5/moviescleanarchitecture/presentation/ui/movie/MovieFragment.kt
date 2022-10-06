package com.fabledt5.moviescleanarchitecture.presentation.ui.movie

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
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
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.hide
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import com.fabledt5.moviescleanarchitecture.presentation.utils.setBackgroundTint
import com.fabledt5.moviescleanarchitecture.presentation.utils.setDrawableDivider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class MovieFragment : Fragment(R.layout.fragment_movie) {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModel.Factory

    private val binding: FragmentMovieBinding by viewBinding()
    private val navArgs: MovieFragmentArgs by navArgs()

    private val movieViewModel by viewModels<MovieViewModel>(
        factoryProducer = {
            MovieViewModel.provideFactory(
                movieViewModelFactory,
                navArgs.movieId
            )
        }
    )

    private val onSimilarMoviesClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            MovieFragmentDirections.actionMovieFragmentSelf(movieId, moviePoster)
                .also { direction ->
                    findNavController().navigate(direction)
                }
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

    private val genresAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieGenresAdapter()
    }

    private val movieCastAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieCastListAdapter(onPersonClickListener)
    }

    private val similarMoviesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SimilarMoviesListAdapter(onSimilarMoviesClickListener)
    }

    private val movieDetailsBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
        get() = BottomSheetBehavior.from(binding.movieDetailsBottomSheet.bottomSheet)

    private val moviePosterSizeChangeListener =
        View.OnLayoutChangeListener { _, _, top, _, bottom, _, _, _, _ ->
            if (bottom != top) {
                binding.civPlayButton.animateAlpha(targetAlpha = 1f)
            }
        }

    private val movieOverviewSizeChangeListener =
        View.OnLayoutChangeListener { _, _, top, _, _, _, _, _, _ ->
            if (top != 0 && binding.movieDetailsBottomSheet.tvMovieTitle.text.isNotEmpty()) {
                movieDetailsBottomSheetBehavior.peekHeight = top + 15
                setMoviePosterSize()
            }
        }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
        observeMovieDetails()
    }

    private fun initUi() {
        binding.ivImagePoster.load(navArgs.moviePoster) {
            crossfade(true)
            error(R.drawable.poster_placeholder)
        }

        with(binding.movieDetailsBottomSheet) {
            rvCast.adapter = movieCastAdapter
            rvCast.setDrawableDivider(R.drawable.lists_divider)

            rvGenres.adapter = genresAdapter
            rvGenres.setDrawableDivider(R.drawable.lists_divider)

            rvMoreLikeThis.adapter = similarMoviesAdapter
            rvMoreLikeThis.setDrawableDivider(R.drawable.lists_divider)
        }
    }

    private fun setupListeners() = with(binding.movieDetailsBottomSheet) {
        btnWant.setOnClickListener { movieViewModel.addMovieToWanted() }
        btnWatched.setOnClickListener { movieViewModel.addMovieToWatched() }

        tvMovieOverview.addOnLayoutChangeListener(movieOverviewSizeChangeListener)
        binding.ivImagePoster.addOnLayoutChangeListener(moviePosterSizeChangeListener)
    }

    private fun observeMovieDetails() {
        movieViewModel.movieDetails
            .onEach { result ->
                when (result) {
                    is Resource.Success -> showResult(result.data)
                    is Resource.Error -> showMovieLoadingError()
                    else -> Unit
                }
            }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieType
            .onEach { type ->
                when (type) {
                    MovieType.WATCHED -> selectWatchButton()
                    MovieType.WANT -> selectWantButton()
                    MovieType.NONE -> clearButtons()
                }
            }.launchWhenStarted(lifecycleScope)

        movieViewModel.movieCast
            .onEach { result ->
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

        movieViewModel.similarMovies
            .onEach { result ->
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

        movieViewModel.movieTrailer
            .onEach { result ->
                when (result) {
                    is Resource.Error -> Timber.e(result.message)
                    is Resource.Success -> enableTrailerButton(result.data)
                    else -> Unit
                }
            }.launchWhenStarted(lifecycleScope)
    }

    private fun showResult(movie: MovieItem?) = with(binding.movieDetailsBottomSheet) {
        movie?.let {
            tvMovieTitle.text = it.movieTitle
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

    private fun setMoviePosterSize() {
        val bottomSheetPeekHeight = movieDetailsBottomSheetBehavior.peekHeight

        if (bottomSheetPeekHeight == 0) return

        val screenHeight = resources.displayMetrics.heightPixels
        val imageAvailableSpace = screenHeight -
                bottomSheetPeekHeight -
                resources.getDimension(R.dimen._10sdp).toInt() * 2

        binding.ivImagePoster.layoutParams.height = imageAvailableSpace
    }

    private fun enableTrailerButton(url: String) = with(binding) {
        civPlayButton.setOnClickListener {
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$url"))
            try {
                context?.startActivity(youtubeIntent)
            } catch (exception: ActivityNotFoundException) {
                MovieFragmentDirections.actionMovieFragmentToTrailerFragment(url)
                    .also { direction ->
                        findNavController().navigate(direction)
                    }
            }
        }
    }

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
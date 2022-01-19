package com.fabledt5.moviescleanarchitecture.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details.MovieUseCaseWrapper
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import com.fabledt5.moviescleanarchitecture.presentation.model.MovieData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieUseCaseWrapper: MovieUseCaseWrapper) :
    ViewModel() {

    private val moviesStack = Stack<MovieData>()
    private var currentMovieId = -1

    private val _canNavigateBack = MutableStateFlow(value = false)
    val canNavigateBack = _canNavigateBack.asStateFlow()

    private val _movieDetails = MutableStateFlow<Resource<MovieItem>>(value = Resource.Loading)
    val movieDetails = _movieDetails.asStateFlow()

    private val _moviePoster = MutableStateFlow<String?>(value = null)
    val moviePoster = _moviePoster.asStateFlow()

    private val _movieCast =
        MutableStateFlow<Resource<List<PersonItem>>>(value = Resource.Loading)
    val movieCast = _movieCast.asStateFlow()

    private val _similarMovies =
        MutableStateFlow<Resource<List<MovieItem>>>(value = Resource.Loading)
    val similarMovies = _similarMovies.asStateFlow()

    private val _movieTrailer = MutableStateFlow<Resource<String>>(value = Resource.Loading)
    val movieTrailer = _movieTrailer.asStateFlow()

    private val _movieType = MutableStateFlow(value = MovieType.NONE)
    val movieType = _movieType.asStateFlow()

    fun changeMovie(movieId: Int, moviePoster: String?) {
        if (currentMovieId == movieId) return
        else {
            when (currentMovieId) {
                -1 -> switchMovie(movieId, moviePoster)
                else -> {
                    addCurrentMovieToBackStack()
                    switchMovie(movieId, moviePoster)
                }
            }
        }
    }

    private fun switchMovie(movieId: Int, moviePoster: String?) {
        currentMovieId = movieId
        _moviePoster.value = moviePoster
        getMovieInfo()
        observeMovieType()
    }

    private fun addCurrentMovieToBackStack() {
        val movieData = MovieData(
            movieId = currentMovieId,
            moviePoster = _moviePoster.value,
            movieType = _movieType.value,
            movieCast = _movieCast.value,
            movieDetails = _movieDetails.value,
            movieTrailer = _movieTrailer.value,
            similarMovies = _similarMovies.value
        )
        moviesStack.push(movieData)
        _canNavigateBack.value = true
    }

    fun addMovieToWanted() = viewModelScope.launch {
        (_movieDetails.value as Resource.Success).let {
            movieUseCaseWrapper.saveMovie(it.data, MovieType.WANT)
        }
    }

    fun addMovieToWatched() = viewModelScope.launch {
        (_movieDetails.value as Resource.Success).let {
            movieUseCaseWrapper.saveMovie(it.data, MovieType.WATCHED)
        }
    }

    fun getMovieFromBackStack() {
        val movieData = moviesStack.pop()
        currentMovieId = movieData.movieId
        _movieDetails.value = movieData.movieDetails
        _moviePoster.value = movieData.moviePoster
        _movieType.value = movieData.movieType
        _movieCast.value = movieData.movieCast
        _similarMovies.value = movieData.similarMovies
        _movieTrailer.value = movieData.movieTrailer
        observeMovieType()

        if (moviesStack.size == 0) _canNavigateBack.value = false
    }

    private fun getMovieInfo() {
        movieUseCaseWrapper.getMovieDetails(movieId = currentMovieId).onEach { movie ->
            _movieDetails.value = movie
        }.launchIn(viewModelScope)

        movieUseCaseWrapper.getMovieCast(movieId = currentMovieId).onEach { cast ->
            _movieCast.value = cast
        }.launchIn(viewModelScope)

        movieUseCaseWrapper.getSimilarMovies(movieId = currentMovieId).onEach { movies ->
            _similarMovies.value = movies
        }.launchIn(viewModelScope)

        movieUseCaseWrapper.getMovieTrailer(movieId = currentMovieId).onEach { trailerPath ->
            _movieTrailer.value = trailerPath
        }.launchIn(viewModelScope)
    }

    private fun observeMovieType() =
        movieUseCaseWrapper.getMovieType(movieId = currentMovieId).onEach { type ->
            _movieType.value = type
        }.launchIn(viewModelScope)

    fun onDetach() {
        currentMovieId = -1
    }
}
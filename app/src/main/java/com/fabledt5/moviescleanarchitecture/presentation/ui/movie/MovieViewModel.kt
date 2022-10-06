package com.fabledt5.moviescleanarchitecture.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details.MovieCases
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel @AssistedInject constructor(
    @Assisted movieId: Int,
    private val movieCases: MovieCases
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            movieId: Int,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId) as T
            }

        }
    }

    private val _movieDetails = MutableStateFlow<Resource<MovieItem>>(value = Resource.Loading)
    val movieDetails = _movieDetails.asStateFlow()

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

    init {
        getMovieInfo(movieId = movieId)
        observeMovieType(movieId = movieId)
    }

    fun addMovieToWanted() = viewModelScope.launch(Dispatchers.IO) {
        (_movieDetails.value as? Resource.Success)?.let {
            movieCases.saveMovie(it.data, MovieType.WANT)
        }
    }

    fun addMovieToWatched() = viewModelScope.launch(Dispatchers.IO) {
        (_movieDetails.value as? Resource.Success)?.let {
            movieCases.saveMovie(it.data, MovieType.WATCHED)
        }
    }

    private fun getMovieInfo(movieId: Int) {
        movieCases.getMovieDetails(movieId = movieId)
            .onEach { movie ->
                _movieDetails.update { movie }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        movieCases.getMovieCast(movieId = movieId)
            .onEach { cast ->
                _movieCast.update { cast }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        movieCases.getSimilarMovies(movieId = movieId)
            .onEach { movies ->
                _similarMovies.update { movies }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        movieCases.getMovieTrailer(movieId = movieId)
            .onEach { trailerPath ->
                _movieTrailer.update { trailerPath }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun observeMovieType(movieId: Int) =
        movieCases.getMovieType(movieId = movieId)
            .onEach { type ->
                _movieType.update { type }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

}
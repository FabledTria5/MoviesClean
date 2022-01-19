package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.use_case.trending.GetTrendingMovies
import com.fabledt5.moviescleanarchitecture.domain.use_case.trending.GetTrendingShows
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatus
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatusListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val getTrendingMovies: GetTrendingMovies,
    private val getTrendingShows: GetTrendingShows,
    networkStatusListener: NetworkStatusListener,
) : ViewModel() {

    private val _trendingMovies = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _trendingShows = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val trendingShows = _trendingShows.asStateFlow()

    init {
        networkStatusListener.networkStatus.onEach { status ->
            when (status) {
                NetworkStatus.Available -> loadContent()
                NetworkStatus.Unavailable -> {
                    if (_trendingMovies.value !is Resource.Success) {
                        _trendingMovies.value = Resource.Error(message = "No internet connection")
                    }
                    if (_trendingShows.value !is Resource.Success) {
                        _trendingShows.value = Resource.Error(message = "No internet connection")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadContent() {
        getTrendingMovies().onEach { result ->
            _trendingMovies.value = result
        }.launchIn(viewModelScope)

        getTrendingShows().onEach { result ->
            _trendingShows.value = result
        }.launchIn(viewModelScope)
    }

}
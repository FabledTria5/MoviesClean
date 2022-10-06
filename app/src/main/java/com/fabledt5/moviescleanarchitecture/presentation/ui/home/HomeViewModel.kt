package com.fabledt5.moviescleanarchitecture.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.use_case.trending.GetTrendingMovies
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatus
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatusListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    networkStatusListener: NetworkStatusListener,
    private val getTrendingMovies: GetTrendingMovies,
) : ViewModel() {

    private val _trendingMovies = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val trendingMovies = _trendingMovies.asStateFlow()

    init {
        viewModelScope.launch {
            networkStatusListener.networkStatus.collectLatest { status ->
                when (status) {
                    NetworkStatus.Available -> {
                        if (_trendingMovies.value !is Resource.Success)
                            loadContent()
                    }

                    NetworkStatus.Unavailable -> {
                        if (_trendingMovies.value !is Resource.Success) {
                            _trendingMovies.value =
                                Resource.Error(message = "No internet connection")
                        }
                    }
                }
            }
        }
    }

    private fun loadContent() {
        getTrendingMovies()
            .onEach { result ->
                _trendingMovies.update { result }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

}
package com.fabledt5.moviescleanarchitecture.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fabledt5.moviescleanarchitecture.domain.use_case.search.SearchMovies
import com.fabledt5.moviescleanarchitecture.domain.use_case.search.SearchPersons
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val searchMoviesCase: SearchMovies,
    private val searchPersonsCase: SearchPersons,
) : ViewModel() {

    val currentSearchQuery = MutableStateFlow("")

    val moviesList = currentSearchQuery.flatMapLatest { query ->
        searchMoviesCase(searchQuery = query, includeAdult = false).cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val personsList = currentSearchQuery.flatMapLatest { query ->
        searchPersonsCase(searchQuery = query).cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}
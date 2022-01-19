package com.fabledt5.moviescleanarchitecture.domain.use_case.search

import com.fabledt5.moviescleanarchitecture.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMovies @Inject constructor(
    private val searchRepository: SearchRepository
) {

    operator fun invoke(searchQuery: String, includeAdult: Boolean) =
        searchRepository.searchMovies(query = searchQuery, includeAdult = includeAdult)

}
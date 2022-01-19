package com.fabledt5.moviescleanarchitecture.domain.use_case.search

import com.fabledt5.moviescleanarchitecture.domain.repository.SearchRepository
import javax.inject.Inject

class SearchPersons @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(searchQuery: String) = searchRepository.searchPersons(query = searchQuery)
}
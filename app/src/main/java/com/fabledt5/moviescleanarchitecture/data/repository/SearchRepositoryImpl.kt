package com.fabledt5.moviescleanarchitecture.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fabledt5.moviescleanarchitecture.data.paging.SearchMoviesPagingSource
import com.fabledt5.moviescleanarchitecture.data.paging.SearchPeoplePagingSource
import com.fabledt5.moviescleanarchitecture.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchMoviesPagingSourceFactory: SearchMoviesPagingSource.Factory,
    private val searchPeoplePagingSource: SearchPeoplePagingSource.Factory
) : SearchRepository {

    override fun searchMovies(
        query: String,
        includeAdult: Boolean
    ) = Pager(PagingConfig(pageSize = 20)) {
        searchMoviesPagingSourceFactory.create(query, includeAdult)
    }.flow

    override fun searchPersons(query: String) = Pager(PagingConfig(pageSize = 20)) {
        searchPeoplePagingSource.create(query)
    }.flow

}

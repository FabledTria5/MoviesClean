package com.fabledt5.moviescleanarchitecture.domain.repository

import androidx.paging.PagingData
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchMovies(
        query: String,
        includeAdult: Boolean
    ): Flow<PagingData<MovieItem>>

    fun searchPersons(query: String): Flow<PagingData<PersonItem>>
}
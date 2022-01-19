package com.fabledt5.moviescleanarchitecture.domain.repository

import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState
import kotlinx.coroutines.flow.Flow

interface ProfileMoviesDataStoreRepository {
    suspend fun persistSortState(sortState: SortState)
    suspend fun persistListShape(listsShape: Int)

    val readSortState: Flow<SortState>
    val readListsShape: Flow<MovieListShape>
}
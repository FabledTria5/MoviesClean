package com.fabledt5.moviescleanarchitecture.domain.repository

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    fun getTrendingMovies(): Flow<List<MovieItem>>

}
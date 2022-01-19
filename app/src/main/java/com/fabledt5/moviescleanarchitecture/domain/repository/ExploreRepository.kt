package com.fabledt5.moviescleanarchitecture.domain.repository

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem

interface ExploreRepository {
    suspend fun getTrendingMovies(): List<MovieItem>?
    suspend fun getTrendingShows(): List<MovieItem>?
}
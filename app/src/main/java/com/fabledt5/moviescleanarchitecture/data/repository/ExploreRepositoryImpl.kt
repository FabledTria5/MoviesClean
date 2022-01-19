package com.fabledt5.moviescleanarchitecture.data.repository

import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.data.api.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.domain.repository.ExploreRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MediaType
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
) : ExploreRepository {

    override suspend fun getTrendingMovies() =
        api.getTrendingContent(mediaType = MediaType.movie.name).toDomain()

    override suspend fun getTrendingShows() =
        api.getTrendingContent(mediaType = MediaType.tv.name).toDomain()

}


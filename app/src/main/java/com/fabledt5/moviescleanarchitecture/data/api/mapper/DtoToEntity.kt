package com.fabledt5.moviescleanarchitecture.data.api.mapper

import com.fabledt5.moviescleanarchitecture.data.api.dto.trending.TrendingResponse
import com.fabledt5.moviescleanarchitecture.data.db.entity.MovieEntity
import com.fabledt5.moviescleanarchitecture.domain.util.Constants
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import java.time.LocalDate

fun TrendingResponse.toEntity(): List<MovieEntity> = trendingResults.map { result ->
    MovieEntity(
        movieId = result.id,
        moviePoster = "${Constants.API_IMAGE_PREFIX}${result.posterPath}",
        movieType = MovieType.TRENDING,
        createdAt = LocalDate.now().toEpochDay()
    )
}
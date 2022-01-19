package com.fabledt5.moviescleanarchitecture.domain.model.items

import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType.NONE

data class MovieItem(
    val movieId: Int = 0,
    val movieType: MovieType = NONE,
    val moviePoster: String? = null,
    val movieRating: Float = 0f,
    val movieTitle: String = "",
    val movieRelease: String = "",
    val movieCountry: String = "",
    val movieRuntime: String = "",
    val movieOverview: String = "",
    val movieGenres: List<String> = listOf()
)
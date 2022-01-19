package com.fabledt5.moviescleanarchitecture.presentation.model

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType

data class MovieData(
    val movieId: Int,
    val movieDetails: Resource<MovieItem>,
    val moviePoster: String?,
    val movieCast: Resource<List<PersonItem>>,
    val similarMovies: Resource<List<MovieItem>>,
    val movieTrailer: Resource<String>,
    val movieType: MovieType
)

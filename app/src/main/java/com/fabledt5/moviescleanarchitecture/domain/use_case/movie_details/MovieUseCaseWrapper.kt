package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import javax.inject.Inject

data class MovieUseCaseWrapper @Inject constructor(
    val getMovieCast: GetMovieCast,
    val getMovieDetails: GetMovieDetails,
    val getMovieTrailer: GetMovieTrailer,
    val getMovieType: GetMovieType,
    val getSimilarMovies: GetSimilarMovies,
    val saveMovie: SaveMovie
)
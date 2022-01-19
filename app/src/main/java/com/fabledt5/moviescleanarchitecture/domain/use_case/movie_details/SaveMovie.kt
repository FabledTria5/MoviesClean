package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import javax.inject.Inject

class SaveMovie @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {

    suspend operator fun invoke(movieItem: MovieItem, movieType: MovieType) {
        val savedMovieId = detailsRepository.getMovieId(movieTitle = movieItem.movieTitle)
        detailsRepository.saveMovie(
            movieItem = movieItem,
            movieType = movieType,
            movieDbId = savedMovieId ?: 0
        )
    }

}

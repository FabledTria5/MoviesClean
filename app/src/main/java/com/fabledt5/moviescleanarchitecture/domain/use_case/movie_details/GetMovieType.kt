package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieType @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {

    operator fun invoke(movieId: Int): Flow<MovieType> =
        detailsRepository.getMovieType(movieId = movieId).map { it ?: MovieType.NONE }

}
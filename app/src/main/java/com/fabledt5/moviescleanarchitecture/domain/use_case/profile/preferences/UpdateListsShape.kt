package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences

import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import javax.inject.Inject

class UpdateListsShape @Inject constructor(
    private val profileMoviesDataStoreRepository: ProfileMoviesDataStoreRepository
) {
    suspend operator fun invoke(listsShape: MovieListShape) {
        val newShapeOrdinal = listsShape.next.ordinal
        profileMoviesDataStoreRepository.persistListShape(newShapeOrdinal)
    }

}
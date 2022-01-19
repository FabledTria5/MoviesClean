package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences

import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import javax.inject.Inject

class ReadSortState @Inject constructor(
    private val profileMoviesDataStoreStoreRepository: ProfileMoviesDataStoreRepository,
) {
    operator fun invoke() = profileMoviesDataStoreStoreRepository.readSortState
}
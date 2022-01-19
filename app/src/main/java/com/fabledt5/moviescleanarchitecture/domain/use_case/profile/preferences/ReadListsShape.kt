package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences

import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import javax.inject.Inject

class ReadListsShape @Inject constructor(
    private val profileMoviesDataStoreRepository: ProfileMoviesDataStoreRepository
) {
    operator fun invoke() = profileMoviesDataStoreRepository.readListsShape
}
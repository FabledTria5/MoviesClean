package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences

import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import javax.inject.Inject

class UpdateSortState @Inject constructor(
    private val profileMoviesDataStoreStoreRepository: ProfileMoviesDataStoreRepository,
) {
    suspend operator fun invoke(sortState: SortState) =
        profileMoviesDataStoreStoreRepository.persistSortState(sortState = sortState)
}
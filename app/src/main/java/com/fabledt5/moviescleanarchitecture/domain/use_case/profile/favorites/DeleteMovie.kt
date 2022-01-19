package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites

import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteMovie @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(movieId: Int) = profileRepository.deleteMovie(movieId = movieId)
}
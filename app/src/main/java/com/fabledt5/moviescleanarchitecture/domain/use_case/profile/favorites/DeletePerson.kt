package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites

import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import javax.inject.Inject

class DeletePerson @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(personId: Int) = profileRepository.deletePerson(personId = personId)
}
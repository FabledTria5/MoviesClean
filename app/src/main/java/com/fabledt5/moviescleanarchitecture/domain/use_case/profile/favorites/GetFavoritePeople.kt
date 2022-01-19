package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites

import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoritePeople @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(isReversed: Boolean) =
        profileRepository.getFavoritePeople().map { persons ->
            if (!isReversed) persons
            else persons.reversed()
        }

}
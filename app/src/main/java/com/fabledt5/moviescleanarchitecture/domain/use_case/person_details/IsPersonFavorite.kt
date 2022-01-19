package com.fabledt5.moviescleanarchitecture.domain.use_case.person_details

import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsPersonFavorite @Inject constructor(
    private val detailsRepository: DetailsRepository
) {
    operator fun invoke(personId: Int) = detailsRepository.isFavoritePerson(personId = personId)
}
package com.fabledt5.moviescleanarchitecture.domain.use_case.person_details

import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import javax.inject.Inject

class SavePerson @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {
    suspend operator fun invoke(personItem: PersonItem) =
        detailsRepository.savePerson(personItem = personItem)
}
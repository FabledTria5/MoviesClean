package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings

import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class UpdateUserImage @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend operator fun invoke(codedUserImage: String) =
        userDataStoreRepository.persistUserImage(codedImage = codedUserImage)
}
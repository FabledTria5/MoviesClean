package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings

import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class UpdateUserName @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend operator fun invoke(name: String) = userDataStoreRepository.persistUserName(name = name)
}
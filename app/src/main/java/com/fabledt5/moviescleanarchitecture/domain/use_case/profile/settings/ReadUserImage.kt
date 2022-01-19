package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings

import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class ReadUserImage @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    operator fun invoke() = userDataStoreRepository.readUserImage
}
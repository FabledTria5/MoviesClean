package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings

import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadUserName @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    operator fun invoke() = userDataStoreRepository.readUserName.map { name ->
        if (name.isNullOrEmpty()) "User name"
        else name
    }
}
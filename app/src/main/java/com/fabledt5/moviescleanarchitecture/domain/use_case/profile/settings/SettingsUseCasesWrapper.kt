package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings

import javax.inject.Inject

data class SettingsUseCasesWrapper @Inject constructor (
    val readUserImage: ReadUserImage,
    val updateUserImage: UpdateUserImage,
    val readUserName: ReadUserName,
    val updateUserName: UpdateUserName
)
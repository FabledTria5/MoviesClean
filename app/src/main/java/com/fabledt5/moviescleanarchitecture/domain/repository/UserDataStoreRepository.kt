package com.fabledt5.moviescleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun persistUserImage(codedImage: String)
    suspend fun persistUserName(name: String)

    val readUserImage: Flow<String?>
    val readUserName: Flow<String?>
}
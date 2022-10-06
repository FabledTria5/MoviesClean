package com.fabledt5.moviescleanarchitecture.presentation.utils.network

import kotlinx.coroutines.flow.Flow

interface NetworkStatusListener {

    val networkStatus: Flow<NetworkStatus>

}
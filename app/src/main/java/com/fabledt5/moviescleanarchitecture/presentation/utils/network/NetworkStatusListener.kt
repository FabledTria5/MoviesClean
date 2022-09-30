package com.fabledt5.moviescleanarchitecture.presentation.utils.network

import kotlinx.coroutines.flow.SharedFlow

interface NetworkStatusListener {

    val networkStatus: SharedFlow<NetworkStatus>

}
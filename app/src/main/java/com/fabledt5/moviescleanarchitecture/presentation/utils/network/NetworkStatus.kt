package com.fabledt5.moviescleanarchitecture.presentation.utils.network

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

package com.fabledt5.moviescleanarchitecture.domain.model

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    object Loading : Resource<Nothing>()
}
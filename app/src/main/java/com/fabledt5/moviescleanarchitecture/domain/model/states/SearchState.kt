package com.fabledt5.moviescleanarchitecture.domain.model.states

sealed class SearchState<out T> {
    class Success<T>(val data: T) : SearchState<T>()
    class MoviesSuccess<T>(val data: T) : SearchState<T>()
    class PersonSuccess<T>(val data: T) : SearchState<T>()
    class Error<T>(val message: String) : SearchState<T>()
    object EmptyResult : SearchState<Nothing>()
    object Loading : SearchState<Nothing>()
    object Idle : SearchState<Nothing>()
}
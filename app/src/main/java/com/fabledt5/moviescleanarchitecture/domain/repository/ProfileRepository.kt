package com.fabledt5.moviescleanarchitecture.domain.repository

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getMoviesList(movieType: MovieType): Flow<List<MovieItem>>
    fun getFavoritePeople(): Flow<List<PersonItem>>

    suspend fun deleteMovie(movieId: Int)
    suspend fun deletePerson(personId: Int)

}
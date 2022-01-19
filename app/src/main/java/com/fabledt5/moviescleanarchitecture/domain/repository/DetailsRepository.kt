package com.fabledt5.moviescleanarchitecture.domain.repository

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieItem?
    suspend fun getMovieVideo(movieId: Int): String?
    suspend fun getSimilarMovies(movieId: Int): List<MovieItem>?
    suspend fun getMovieCast(movieId: Int): List<PersonItem>?

    suspend fun getPersonDetails(personId: Int): PersonItem
    suspend fun getPersonCredits(personId: Int): List<MovieItem>

    fun getMovieType(movieId: Int): Flow<MovieType?>
    fun isFavoritePerson(personId: Int): Flow<Boolean>

    suspend fun getMovieId(movieTitle: String): Int?

    suspend fun saveMovie(movieItem: MovieItem, movieType: MovieType, movieDbId: Int)
    suspend fun savePerson(personItem: PersonItem)

    suspend fun deleteMovie(movieId: Int)
    suspend fun deletePerson(personId: Int)
}


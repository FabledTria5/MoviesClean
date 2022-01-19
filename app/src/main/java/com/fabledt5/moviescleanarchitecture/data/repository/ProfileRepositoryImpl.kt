package com.fabledt5.moviescleanarchitecture.data.repository

import com.fabledt5.moviescleanarchitecture.data.db.dao.MoviesDao
import com.fabledt5.moviescleanarchitecture.data.db.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : ProfileRepository {

    override fun getMoviesList(movieType: MovieType): Flow<List<MovieItem>> =
        moviesDao.getMoviesList(movieType = movieType.ordinal).toDomain()

    override fun getFavoritePeople(): Flow<List<PersonItem>> = moviesDao.getAllPersons().toDomain()

    override suspend fun deleteMovie(movieId: Int) = moviesDao.deleteMovie(movieId = movieId)

    override suspend fun deletePerson(personId: Int) = moviesDao.deletePerson(personId = personId)

}



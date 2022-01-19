package com.fabledt5.moviescleanarchitecture.data.repository

import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.data.api.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.data.db.dao.MoviesDao
import com.fabledt5.moviescleanarchitecture.data.db.mapper.toEntity
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val dao: MoviesDao,
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Int) =
        api.getMovieDetails(movieId = movieId).toDomain()

    override suspend fun getMovieVideo(movieId: Int) =
        api.getMovieVideos(movieId = movieId).toDomain()

    override suspend fun getSimilarMovies(movieId: Int) =
        api.getSimilarMovies(movieId = movieId).toDomain()

    override suspend fun getMovieCast(movieId: Int) =
        api.getMovieCredits(movieId = movieId).toDomain()

    override suspend fun getPersonDetails(personId: Int) =
        api.getPersonDetails(personId = personId).toDomain()

    override suspend fun getPersonCredits(personId: Int) =
        api.getPersonCredits(personId = personId).toDomain()

    override fun getMovieType(movieId: Int) = dao.getMovieType(movieId = movieId)

    override fun isFavoritePerson(personId: Int) = dao.isPersonFavorite(personId = personId)

    override suspend fun getMovieId(movieTitle: String) = dao.getMovieId(movieTitle = movieTitle)

    override suspend fun saveMovie(movieItem: MovieItem, movieType: MovieType, movieDbId: Int) =
        dao.insertMovie(movieEntity = movieItem.toEntity(movieType, movieDbId))

    override suspend fun savePerson(personItem: PersonItem) =
        dao.insertPerson(personEntity = personItem.toEntity())

    override suspend fun deleteMovie(movieId: Int) = dao.deleteMovie(movieId = movieId)

    override suspend fun deletePerson(personId: Int) = dao.deletePerson(personId = personId)

}

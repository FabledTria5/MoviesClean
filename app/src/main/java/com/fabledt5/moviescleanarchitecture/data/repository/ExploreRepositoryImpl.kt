package com.fabledt5.moviescleanarchitecture.data.repository

import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.data.api.mapper.toEntity
import com.fabledt5.moviescleanarchitecture.data.db.dao.MoviesDao
import com.fabledt5.moviescleanarchitecture.data.db.mapper.toDomain
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.repository.ExploreRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val moviesDao: MoviesDao
) : ExploreRepository {

    override fun getTrendingMovies(): Flow<List<MovieItem>> =
        moviesDao.getMoviesList(movieType = MovieType.TRENDING.ordinal)
            .onEach { list ->
                if (list.isEmpty()) {
                    fetchTrendingMovies()
                    return@onEach
                }
                if (LocalDate.now().toEpochDay() - list.first().createdAt > 7) fetchTrendingMovies()
            }
            .toDomain()

    private suspend fun fetchTrendingMovies() {
        val moviesFromApi = api.getTrendingMovies()
        moviesDao.insertMovies(moviesList = moviesFromApi.toEntity())
    }

}


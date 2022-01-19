package com.fabledt5.moviescleanarchitecture.domain.use_case.trending

import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetTrendingShows @Inject constructor(
    private val exploreRepository: ExploreRepository,
) {
    operator fun invoke(): Flow<Resource<List<MovieItem>>> = flow {
        try {
            emit(Resource.Loading)
            val result = exploreRepository.getTrendingShows()
            if (result == null) {
                emit(Resource.Error(message = "Data is null"))
            } else {
                emit(Resource.Success(result))
            }
        } catch (exception: HttpException) {
            emit(Resource.Error(message = exception.message.toString()))
        } catch (exception: Exception) {
            emit(Resource.Error(message = "Unknown error: ${exception.message}"))
        }
    }
}
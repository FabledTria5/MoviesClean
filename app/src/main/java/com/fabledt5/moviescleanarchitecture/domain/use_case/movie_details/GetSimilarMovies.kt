package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetSimilarMovies @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {

    operator fun invoke(movieId: Int) = flow {
        try {
            emit(Resource.Loading)
            val result = detailsRepository.getSimilarMovies(movieId = movieId)
            if (result.isNullOrEmpty()) emit(Resource.Error(message = "No similar movies found"))
            else emit(Resource.Success(result.subList(0, 10)))
        } catch (exception: HttpException) {
            emit(Resource.Error(message = exception.message()))
        } catch (exception: Exception) {
            emit(Resource.Error(message = "Unknown error: ${exception.message}"))
        }
    }

}
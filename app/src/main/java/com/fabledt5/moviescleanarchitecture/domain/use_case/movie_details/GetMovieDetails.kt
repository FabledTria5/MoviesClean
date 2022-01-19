package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val detailsRepository: DetailsRepository
) {

    operator fun invoke(movieId: Int) = flow {
        try {
            emit(Resource.Loading)
            val movieItem = detailsRepository.getMovieDetails(movieId = movieId)
            if (movieItem == null) emit(Resource.Error(message = "Result is null"))
            else emit(Resource.Success(data = movieItem))
        } catch (exception: HttpException) {
            emit(Resource.Error(message = exception.message()))
        } catch (exception: Exception) {
            emit(Resource.Error(message = "Unknown error: ${exception.message}"))
        }
    }

}
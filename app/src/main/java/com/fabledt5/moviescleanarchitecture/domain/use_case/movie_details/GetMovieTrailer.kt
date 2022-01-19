package com.fabledt5.moviescleanarchitecture.domain.use_case.movie_details

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetMovieTrailer @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {
    operator fun invoke(movieId: Int) = flow {
        try {
            emit(Resource.Loading)
            val response = detailsRepository.getMovieVideo(movieId = movieId)
            if (response == null) emit(Resource.Error("No trailers found"))
            else emit(Resource.Success(data = response))
        } catch (exception: HttpException) {
            emit(Resource.Error("Network error: ${exception.message()}"))
        } catch (exception: Exception) {
            emit(Resource.Error("Unknown error: ${exception.message}"))
        }
    }
}
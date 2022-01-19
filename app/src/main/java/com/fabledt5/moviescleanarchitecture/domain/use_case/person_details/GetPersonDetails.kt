package com.fabledt5.moviescleanarchitecture.domain.use_case.person_details

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetPersonDetails @Inject constructor(
    private val detailsRepository: DetailsRepository,
) {
    operator fun invoke(personId: Int) = flow {
        try {
            emit(Resource.Loading)
            emit(Resource.Success(detailsRepository.getPersonDetails(personId = personId)))
        } catch (exception: HttpException) {
            emit(Resource.Error(message = "Network Error: ${exception.message()}"))
        } catch (exception: Exception) {
            emit(Resource.Error(message = "Unexpected exception: ${exception.message}"))
        }
    }
}
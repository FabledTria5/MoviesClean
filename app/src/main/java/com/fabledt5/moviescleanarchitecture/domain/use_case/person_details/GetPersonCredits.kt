package com.fabledt5.moviescleanarchitecture.domain.use_case.person_details

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetPersonCredits @Inject constructor(
    private val detailsRepository: DetailsRepository
) {
    operator fun invoke(personId: Int) = flow {
        try {
            emit(Resource.Loading)
            val data = detailsRepository.getPersonCredits(personId = personId)
            if (data.isEmpty()) emit(Resource.Error("No credits found"))
            else emit(Resource.Success(detailsRepository.getPersonCredits(personId = personId)))
        } catch (exception: HttpException) {
            emit(Resource.Error(message = "Network Error: ${exception.message()}"))
        } catch (exception: RuntimeException) {
            emit(Resource.Error(message = "Unexpected exception: ${exception.message}"))
        }
    }
}
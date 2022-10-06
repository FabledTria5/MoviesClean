package com.fabledt5.moviescleanarchitecture.domain.use_case.trending

import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.repository.ExploreRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingMovies @Inject constructor(
    private val exploreRepository: ExploreRepository,
) {

    operator fun invoke() = exploreRepository.getTrendingMovies().map {
        if (it.isEmpty()) Resource.Error(message = "Empty list")
        else Resource.Success(data = it)
    }

}
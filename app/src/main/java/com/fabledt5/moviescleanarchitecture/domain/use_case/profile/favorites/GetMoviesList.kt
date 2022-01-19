package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites

import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesList @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(movieType: MovieType, sortState: SortState, isReversed: Boolean) =
        profileRepository.getMoviesList(movieType = movieType).map { movies ->
            when (isReversed) {
                true -> when (sortState) {
                    SortState.ALPHABETICAL -> movies.sortedByDescending { it.movieTitle.lowercase() }
                    SortState.RELEASE -> movies.sortedByDescending { it.movieRelease }
                    SortState.RATING -> movies.sortedByDescending { it.movieRating }
                    else -> movies.reversed()
                }
                false -> when (sortState) {
                    SortState.ALPHABETICAL -> movies.sortedBy { it.movieTitle.lowercase() }
                    SortState.RELEASE -> movies.sortedBy { it.movieRelease }
                    SortState.RATING -> movies.sortedBy { it.movieRating }
                    else -> movies
                }
            }
        }

}
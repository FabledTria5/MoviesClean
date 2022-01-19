package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites

import javax.inject.Inject

data class FavoritesUseCasesWrapper @Inject constructor(
    val getWantedMovies: GetMoviesList,
    val getWatchedMovies: GetMoviesList,
    val getFavoritePeople: GetFavoritePeople,
    val deleteMovie: DeleteMovie,
    val  deletePerson: DeletePerson
)
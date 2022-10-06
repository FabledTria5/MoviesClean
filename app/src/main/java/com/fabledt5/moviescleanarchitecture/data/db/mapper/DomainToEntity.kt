package com.fabledt5.moviescleanarchitecture.data.db.mapper

import com.fabledt5.moviescleanarchitecture.data.db.entity.MovieEntity
import com.fabledt5.moviescleanarchitecture.data.db.entity.PersonEntity
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

@JvmName("toEntityMovieItem")
fun MovieItem.toEntity(movieType: MovieType, movieDbId: Int) = MovieEntity(
    id = movieDbId,
    movieId = movieId,
    movieType = movieType,
    moviePoster = moviePoster,
    movieRating = movieRating,
    movieTitle = movieTitle,
    movieRelease = movieRelease,
    movieCountry = movieCountry,
    movieRuntime = movieRuntime,
    createdAt = LocalDate.now().toEpochDay()
)

@JvmName("toEntityPersonItem")
fun PersonItem.toEntity() = PersonEntity(
    personId = personId,
    personImage = personImage,
    personName = personName,
    placeOfBirth = personPlaceOfBirth
)

@JvmName("toDomainMovieEntity")
fun Flow<List<MovieEntity>>.toDomain(): Flow<List<MovieItem>> = map { moviesList ->
    moviesList.map { entity ->
        MovieItem(
            movieId = entity.movieId,
            moviePoster = entity.moviePoster,
            movieRating = entity.movieRating,
            movieTitle = entity.movieTitle,
            movieRuntime = entity.movieRuntime,
            movieCountry = entity.movieCountry,
            movieRelease = entity.movieRelease
        )
    }
}

@JvmName("toDomainPersonEntity")
fun Flow<List<PersonEntity>>.toDomain(): Flow<List<PersonItem>> = map { personsList ->
    personsList.map { entity ->
        PersonItem(
            personId = entity.personId,
            personName = entity.personName,
            personImage = entity.personImage,
            personPlaceOfBirth = entity.placeOfBirth
        )
    }
}
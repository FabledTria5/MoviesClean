package com.fabledt5.moviescleanarchitecture.data.api.mapper

import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_credits.MovieCreditsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_details.MovieDetailsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_search.MovieSearchResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_videos.MovieVideosResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_credits.PersonCreditsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_details.PersonDetailsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_search.PersonSearchResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.trending.TrendingResponse
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.API_IMAGE_PREFIX
import com.fabledt5.moviescleanarchitecture.domain.util.getFirst
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@JvmName("toDomainTrendingMoviesResponse")
fun TrendingResponse.toDomain(): List<MovieItem> = trendingResults.map { result ->
    MovieItem(
        movieId = result.id,
        moviePoster = "$API_IMAGE_PREFIX${result.posterPath}"
    )
}

@JvmName("toDomainMovieDetailsResponse")
fun MovieDetailsResponse.toDomain(): MovieItem = MovieItem(
    movieId = id,
    moviePoster = "$API_IMAGE_PREFIX$posterPath",
    movieRuntime = getRuntime(runtime),
    movieRating = voteAverage.run {
        val df = DecimalFormat("#,#")
        df.roundingMode = RoundingMode.CEILING
        df.format(this).toFloat()
    },
    movieRelease = releaseDate.take(n = 4),
    movieTitle = title,
    movieCountry = if (productionCountries.isNotEmpty()) productionCountries[0].iso31661 else "Unknown",
    movieOverview = overview ?: "",
    movieGenres = genres.map { it.name }
)

@JvmName("toDomainMoviesSearchResponse")
fun MovieSearchResponse.toDomain() = movieSearchResults.map { result ->
    MovieItem(
        movieId = result.id,
        moviePoster = "$API_IMAGE_PREFIX${result.posterPath}"
    )
}

@JvmName("toDomainPersonSearchResponse")
fun PersonSearchResponse.toDomain() = personSearchResults.map { result ->
    PersonItem(
        personId = result.id,
        personName = result.name,
        personImage = "$API_IMAGE_PREFIX${result.profilePath}"
    )
}

@JvmName("toDomainMovieVideo")
fun MovieVideosResponse.toDomain(): String? {
    if (results.isEmpty()) return null
    return results.last { it.site.lowercase() == "youtube" }.key
}

@JvmName("toDomainMovieCredits")
fun MovieCreditsResponse.toDomain() = cast.map { castItem ->
    PersonItem(
        personId = castItem.id,
        personName = castItem.name,
        personImage = "$API_IMAGE_PREFIX${castItem.profilePath}"
    )
}

@JvmName("toDomainPersonDetails")
fun PersonDetailsResponse.toDomain() = PersonItem(
    personId = id,
    personName = name,
    personImage = "$API_IMAGE_PREFIX${profilePath}",
    personBirthday = getBirthday(birthday),
    personPlaceOfBirth = placeOfBirth ?: "",
    personBiography = biography
)

@JvmName("toDomainPersonDetails")
fun PersonCreditsResponse.toDomain() = cast.filter { it.posterPath != null }
    .getFirst(elements = 10)
    .roundListSize()
    .map { castItem ->
        MovieItem(
            movieId = castItem.id,
            moviePoster = "$API_IMAGE_PREFIX${castItem.posterPath}"
        )
    }

private fun getBirthday(birthday: String?): String {
    if (birthday == null) return ""

    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(birthday)!!
    val calendar = Calendar.getInstance().also { it.time = date }

    return SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(calendar.time)
}

private fun getRuntime(runtime: Int?) = runtime?.let {
    val hours = it / 60
    val minutes = it % 60
    String.format("%dh %02dmin", hours, minutes)
} ?: "Unknown"

private fun <T> List<T>.roundListSize() = when {
    size > 9 -> this.subList(0, 9)
    size in 7..8 -> this.subList(0, 5)
    size in 4..5 -> this.subList(0, 2)
    else -> this
}
package com.fabledt5.moviescleanarchitecture.data.api

import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_credits.MovieCreditsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_details.MovieDetailsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_search.MovieSearchResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.movie_videos.MovieVideosResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_credits.PersonCreditsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_details.PersonDetailsResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.person_search.PersonSearchResponse
import com.fabledt5.moviescleanarchitecture.data.api.dto.trending.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET(value = "3/trending/movie/week")
    suspend fun getTrendingMovies(): TrendingResponse

    @GET(value = "3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path(value = "movie_id") movieId: Int,
    ): MovieDetailsResponse

    @GET(value = "3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path(value = "movie_id") movieId: Int,
    ): MovieCreditsResponse

    @GET(value = "3/movie/{movie_id}/recommendations")
    suspend fun getSimilarMovies(
        @Path(value = "movie_id") movieId: Int
    ): MovieSearchResponse

    @GET(value = "3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path(value = "movie_id") movieId: Int
    ): MovieVideosResponse

    @GET(value = "3/search/movie")
    suspend fun searchMovies(
        @Query(value = "query") searchQuery: String,
        @Query(value = "page") page: Int,
        @Query(value = "include_adult") includeAdult: Boolean,
    ): Response<MovieSearchResponse>

    @GET(value = "3/search/person")
    suspend fun searchPeople(
        @Query(value = "query") searchQuery: String,
        @Query(value = "page") page: Int,
    ): Response<PersonSearchResponse>

    @GET(value = "3/person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int
    ): PersonDetailsResponse

    @GET(value = "3/person/{person_id}/movie_credits")
    suspend fun getPersonCredits(
        @Path("person_id") personId: Int
    ): PersonCreditsResponse
}
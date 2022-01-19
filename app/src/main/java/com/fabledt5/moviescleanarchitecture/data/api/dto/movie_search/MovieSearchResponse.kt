package com.fabledt5.moviescleanarchitecture.data.api.dto.movie_search

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieSearchResults: List<MovieSearchResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
package com.fabledt5.moviescleanarchitecture.data.api.dto.person_search


import com.google.gson.annotations.SerializedName

data class PersonSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val personSearchResults: List<PersonSearchResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
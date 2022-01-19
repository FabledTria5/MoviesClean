package com.fabledt5.moviescleanarchitecture.data.api.dto.trending

import com.google.gson.annotations.SerializedName

data class TrendingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val trendingResults: List<TrendingResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
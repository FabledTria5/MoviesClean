package com.fabledt5.moviescleanarchitecture.data.api.dto.movie_videos

import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)
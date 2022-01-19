package com.fabledt5.moviescleanarchitecture.data.api.dto.movie_credits

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)
package com.fabledt5.moviescleanarchitecture.data.api.dto.person_credits

import com.google.gson.annotations.SerializedName

data class PersonCreditsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)
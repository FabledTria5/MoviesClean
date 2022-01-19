package com.fabledt5.moviescleanarchitecture.data.api.dto.person_search

import com.google.gson.annotations.SerializedName

data class PersonSearchResult(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for")
    val knownFor: List<KnownFor>,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
)
package com.fabledt5.moviescleanarchitecture.domain.model.items

data class PersonItem(
    val personId: Int = 0,
    val personName: String = "",
    val personImage: String? = null,
    val personBirthday: String = "",
    val personPlaceOfBirth: String = "",
    val personBiography: String = ""
)
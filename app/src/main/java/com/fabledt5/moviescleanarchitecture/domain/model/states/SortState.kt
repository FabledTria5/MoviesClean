package com.fabledt5.moviescleanarchitecture.domain.model.states

enum class SortState(val sortName: String) {
    ALPHABETICAL(sortName = "A - B"),
    DATE(sortName = "Date Added"),
    RELEASE(sortName = "Release Date"),
    RATING(sortName = "Rating")
}
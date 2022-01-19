package com.fabledt5.moviescleanarchitecture.domain.model.states

enum class MovieListShape(val spanCount: Int) {
    BIG(spanCount = 1) {
        override val next: MovieListShape
            get() = SMALL
    },

    MEDIUM(spanCount = 2) {
        override val next: MovieListShape
            get() = BIG
    },

    SMALL(spanCount = 3) {
        override val next: MovieListShape
            get() = MEDIUM
    };

    abstract val next: MovieListShape
}
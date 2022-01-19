package com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners

interface OnMovieClickListener {
    fun onMovieClick(movieId: Int, moviePoster: String? = null)
}
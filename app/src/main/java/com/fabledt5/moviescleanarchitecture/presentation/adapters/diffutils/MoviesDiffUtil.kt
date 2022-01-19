package com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem

class MoviesDiffUtil : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem) =
        oldItem.movieId == newItem.movieId

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem) = oldItem == newItem
}
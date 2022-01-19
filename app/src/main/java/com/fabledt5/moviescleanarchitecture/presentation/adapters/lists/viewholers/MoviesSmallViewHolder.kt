package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemMovieProfileSmallBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class MoviesSmallViewHolder(private val binding: ItemMovieProfileSmallBinding) :
    RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(ItemMovieProfileSmallBinding.bind(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie_profile_small,
            parent,
            false
        )
    ))

    fun bind(movieItem: MovieItem, onMovieClickListener: OnMovieClickListener) = with(binding) {
        ivMoviePoster.load(movieItem.moviePoster)
        tvMovieRating.text = movieItem.movieRating.toInt().toString()

        root.setOnClickListener {
            onMovieClickListener.onMovieClick(
                movieId = movieItem.movieId,
                moviePoster = movieItem.moviePoster
            )
        }
    }
}
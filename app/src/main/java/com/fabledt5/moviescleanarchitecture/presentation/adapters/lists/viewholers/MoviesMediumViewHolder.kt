package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemMovieProfileMediumBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class MoviesMediumViewHolder(private val binding: ItemMovieProfileMediumBinding) :
    RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(ItemMovieProfileMediumBinding.bind(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie_profile_medium,
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
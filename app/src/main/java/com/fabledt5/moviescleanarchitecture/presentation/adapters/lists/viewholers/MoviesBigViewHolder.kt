package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemMovieProfileBigBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class MoviesBigViewHolder(private val binding: ItemMovieProfileBigBinding) :
    RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(
        ItemMovieProfileBigBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_profile_big,
                parent,
                false
            )
        )
    )

    fun bind(
        movieItem: MovieItem,
        onMovieClickListener: OnMovieClickListener,
        onDeleteMovieClickListener: (movieId: Int) -> Unit
    ) = with(binding) {
        ivMoviePoster.load(movieItem.moviePoster)
        tvMovieTitle.text = movieItem.movieTitle
        tvMovieReleaseYear.text = movieItem.movieRelease
        tvMovieCountry.text = movieItem.movieCountry
        tvMovieRuntime.text = movieItem.movieRuntime
        tvMovieRating.text = movieItem.movieRating.toInt().toString()

        layoutMovie.setOnClickListener {
            onMovieClickListener.onMovieClick(
                movieId = movieItem.movieId,
                moviePoster = movieItem.moviePoster
            )
        }
        layoutDelete.setOnClickListener { onDeleteMovieClickListener(movieItem.movieId) }
    }
}
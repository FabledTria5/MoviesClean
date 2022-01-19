package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemMovieHomeBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.MoviesDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class HomeMoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    ListAdapter<MovieItem, HomeMoviesListAdapter.HomeMoviesListViewHolder>(MoviesDiffUtil()) {

    inner class HomeMoviesListViewHolder(private val binding: ItemMovieHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.ivMoviePoster.load(item.moviePoster)
            binding.ivMoviePoster.setOnClickListener {
                onMovieClickListener.onMovieClick(
                    movieId = item.movieId,
                    moviePoster = item.moviePoster,
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeMoviesListViewHolder(
        ItemMovieHomeBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_home,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: HomeMoviesListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemSimilarMovieBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.MoviesDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class SimilarMoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    ListAdapter<MovieItem, SimilarMoviesListAdapter.SimilarMoviesListViewHolder>(MoviesDiffUtil()) {

    inner class SimilarMoviesListViewHolder(private val binding: ItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)
            binding.ivMoviePoster.load(item.moviePoster)

            binding.ivMoviePoster.setOnClickListener {
                onMovieClickListener.onMovieClick(
                    movieId = item.movieId,
                    moviePoster = item.moviePoster
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimilarMoviesListViewHolder(
        ItemSimilarMovieBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_similar_movie,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: SimilarMoviesListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
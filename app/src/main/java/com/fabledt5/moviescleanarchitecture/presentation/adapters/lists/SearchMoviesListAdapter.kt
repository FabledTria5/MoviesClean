package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemMovieSearchBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.MoviesDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class SearchMoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    PagingDataAdapter<MovieItem, SearchMoviesListAdapter.SearchMoviesListViewHolder>(MoviesDiffUtil()) {

    inner class SearchMoviesListViewHolder(private val binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.moviePoster.load(item?.moviePoster) { error(R.drawable.poster_placeholder) }
            binding.moviePoster.setOnClickListener {
                item?.let {
                    onMovieClickListener.onMovieClick(
                        movieId = it.movieId,
                        moviePoster = it.moviePoster,
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchMoviesListViewHolder(
        ItemMovieSearchBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_search,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: SearchMoviesListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
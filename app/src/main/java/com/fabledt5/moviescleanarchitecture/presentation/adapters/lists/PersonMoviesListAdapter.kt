package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemPersonMovieBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.MoviesDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener

class PersonMoviesListAdapter(private val onMovieClickListener: OnMovieClickListener) :
    ListAdapter<MovieItem, PersonMoviesListAdapter.PersonMoviesListViewHolder>(MoviesDiffUtil()) {

    inner class PersonMoviesListViewHolder(private val binding: ItemPersonMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.ivMoviePoster.load(item.moviePoster) { error(R.drawable.poster_placeholder) }
            binding.ivMoviePoster.setOnClickListener {
                onMovieClickListener.onMovieClick(
                    item.movieId,
                    item.moviePoster
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PersonMoviesListViewHolder(
        ItemPersonMovieBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person_movie,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: PersonMoviesListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
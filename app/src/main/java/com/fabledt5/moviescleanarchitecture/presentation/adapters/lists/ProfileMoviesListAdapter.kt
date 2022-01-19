package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apachat.swipereveallayout.core.ViewBinder
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.MoviesDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers.MoviesBigViewHolder
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers.MoviesMediumViewHolder
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.viewholers.MoviesSmallViewHolder

class ProfileMoviesListAdapter(
    private val onMovieClickListener: OnMovieClickListener,
    private val onDeleteMovieClickListener: (movieId: Int) -> Unit,
    private val layoutManager: GridLayoutManager? = null,
) : ListAdapter<MovieItem, RecyclerView.ViewHolder>(MoviesDiffUtil()) {

    private val viewBinderHelper = ViewBinder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        MovieListShape.SMALL.ordinal -> MoviesSmallViewHolder(parent)
        MovieListShape.MEDIUM.ordinal -> MoviesMediumViewHolder(parent)
        MovieListShape.BIG.ordinal -> MoviesBigViewHolder(parent)
        else -> throw RuntimeException("Unknown list viewType: $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieItem = getItem(position)
        when (holder) {
            is MoviesBigViewHolder -> {
                viewBinderHelper.bind(
                    holder.itemView.findViewById(R.id.swipeRevealLayout),
                    movieItem.movieId.toString()
                )
                holder.bind(
                    movieItem = movieItem,
                    onMovieClickListener = onMovieClickListener,
                    onDeleteMovieClickListener = onDeleteMovieClickListener
                )
            }
            is MoviesMediumViewHolder -> holder.bind(movieItem, onMovieClickListener)
            is MoviesSmallViewHolder -> holder.bind(movieItem, onMovieClickListener)
            else -> throw IllegalArgumentException("Unknown viewHolder: $holder")
        }
    }

    override fun getItemViewType(position: Int) = when (layoutManager?.spanCount) {
        1 -> MovieListShape.BIG.ordinal
        2 -> MovieListShape.MEDIUM.ordinal
        3 -> MovieListShape.SMALL.ordinal
        else -> throw IllegalArgumentException("Unsupported span count for this list")
    }

}
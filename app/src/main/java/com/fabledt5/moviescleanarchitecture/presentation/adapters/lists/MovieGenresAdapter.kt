package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemGenreBinding

class MovieGenresAdapter(private val genresList: ArrayList<String> = arrayListOf()) :
    RecyclerView.Adapter<MovieGenresAdapter.MovieGenresListViewHolder>() {

    inner class MovieGenresListViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: String) {
            binding.tvGenreName.text = genre
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieGenresListViewHolder(
        ItemGenreBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_genre,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: MovieGenresListViewHolder, position: Int) =
        holder.bind(genresList[position])

    override fun getItemCount() = genresList.count()

    fun setItems(genres: List<String>) {
        genresList.addAll(genres)
        notifyItemRangeChanged(0, genres.size)
    }

}
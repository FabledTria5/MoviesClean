package com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil

class GenresDiffUtil : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem === newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

}
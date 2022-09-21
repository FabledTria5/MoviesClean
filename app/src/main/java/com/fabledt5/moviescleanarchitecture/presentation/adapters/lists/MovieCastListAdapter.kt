package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemPersonBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.PersonDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener
import com.fabledt5.moviescleanarchitecture.presentation.utils.GrayScaleTransformation

class MovieCastListAdapter(private val onPersonClickListener: OnPersonClickListener) :
    ListAdapter<PersonItem, MovieCastListAdapter.MovieCastListViewHolder>(PersonDiffUtil()) {

    inner class MovieCastListViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.ivPersonPhoto.load(item.personImage) {
                transformations(GrayScaleTransformation())
                error(R.drawable.person_placeholder)
            }
            binding.tvPersonName.text = item.personName

            binding.root.setOnClickListener {
                onPersonClickListener.onPersonClick(
                    personId = item.personId,
                    personImage = item.personImage
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCastListViewHolder(
        ItemPersonBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: MovieCastListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
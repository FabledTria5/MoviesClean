package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.GrayscaleTransformation
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemPersonBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.PersonDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener

class MovieCastListAdapter(private val onPersonClickListener: OnPersonClickListener) :
    ListAdapter<PersonItem, MovieCastListAdapter.MovieCastListViewHolder>(PersonDiffUtil()) {

    inner class MovieCastListViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            binding.ivPersonPhoto.load(item.personImage) {
                transformations(GrayscaleTransformation())
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
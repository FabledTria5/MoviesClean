package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.GrayscaleTransformation
import com.apachat.swipereveallayout.core.ViewBinder
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemFavoritePersonBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.PersonDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener

class FavoritePeopleListAdapter(
    private val onPersonClickListener: OnPersonClickListener,
    private val onDeletePersonClicked: (Int) -> Unit
) :
    ListAdapter<PersonItem, FavoritePeopleListAdapter.FavoritePeopleListViewHolder>(PersonDiffUtil()) {

    private val viewBinderHelper = ViewBinder()

    inner class FavoritePeopleListViewHolder(private val binding: ItemFavoritePersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(personItem: PersonItem) {
            binding.civPersonImage.load(personItem.personImage) {
                transformations(GrayscaleTransformation())
                error(R.drawable.person_placeholder)
            }
            binding.tvPersonName.text = personItem.personName
            binding.tvPersonPlaceOfBirth.text = personItem.personPlaceOfBirth

            binding.personLayout.setOnClickListener {
                onPersonClickListener.onPersonClick(
                    personId = personItem.personId,
                    personImage = personItem.personImage
                )
            }
            binding.layoutDelete.setOnClickListener { onDeletePersonClicked(personItem.personId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoritePeopleListViewHolder(
            ItemFavoritePersonBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_favorite_person,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: FavoritePeopleListViewHolder, position: Int) {
        val personItem = getItem(position)

        viewBinderHelper.bind(
            holder.itemView.findViewById(R.id.personSwipeReveal),
            personItem.personId.toString()
        )
        holder.bind(personItem = getItem(position))
    }
}
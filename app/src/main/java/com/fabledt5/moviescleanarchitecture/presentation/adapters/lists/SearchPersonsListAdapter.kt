package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemPersonBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils.PersonDiffUtil
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener
import com.fabledt5.moviescleanarchitecture.presentation.utils.GrayScaleTransformation

class SearchPersonsListAdapter(private val onMovieClickListener: OnPersonClickListener) :
    PagingDataAdapter<PersonItem, SearchPersonsListAdapter.SearchPersonsListViewHolder>(
        PersonDiffUtil()
    ) {

    inner class SearchPersonsListViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val item = getItem(itemPosition)

            with(binding) {
                ivPersonPhoto.load(item?.personImage) {
                    transformations(GrayScaleTransformation())
                    error(R.drawable.person_placeholder)
                }
                tvPersonName.text = item?.personName

                root.setOnClickListener {
                    item?.let {
                        onMovieClickListener.onPersonClick(
                            personId = it.personId,
                            personImage = it.personImage
                        )
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchPersonsListViewHolder(
        ItemPersonBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: SearchPersonsListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

}
package com.fabledt5.moviescleanarchitecture.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem

class PersonDiffUtil : DiffUtil.ItemCallback<PersonItem>() {

    override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem) =
        oldItem.personId == newItem.personId

    override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem) = oldItem == newItem

}
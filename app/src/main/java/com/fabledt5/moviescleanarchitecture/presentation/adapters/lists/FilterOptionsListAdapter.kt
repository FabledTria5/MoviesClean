package com.fabledt5.moviescleanarchitecture.presentation.adapters.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.ItemFilterOptionBinding
import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState

class FilterOptionsListAdapter(
    private val sortOptions: List<SortState> = SortState.values().toList(),
    private var selectedItemPosition: Int = 0
) :
    RecyclerView.Adapter<FilterOptionsListAdapter.FilterOptionsViewHolder>() {

    inner class FilterOptionsViewHolder(private val binding: ItemFilterOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) = with(binding) {
            val item = sortOptions[itemPosition]

            tvFilterOptionName.text = item.sortName
            ivFilterSelectedImage.isVisible = itemPosition == selectedItemPosition

            root.setOnClickListener {
                selectedItemPosition = itemPosition
                notifyItemRangeChanged(0, sortOptions.count())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterOptionsViewHolder(
        ItemFilterOptionBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_filter_option,
                parent,
                false
            )
        )
    )

    override fun onBindViewHolder(holder: FilterOptionsViewHolder, position: Int) =
        holder.bind(itemPosition = position)

    override fun getItemCount() = sortOptions.count()

    fun getCurrentSelectedItem() = sortOptions[selectedItemPosition]

}
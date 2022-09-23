package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.databinding.FragmentFilterOptionsBinding
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.FilterOptionsListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class FilterFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val profileViewModel by viewModels<ProfileViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private var _binding: FragmentFilterOptionsBinding? = null
    private val binding get() = _binding!!


    private val filterOptionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FilterOptionsListAdapter(
            selectedItemPosition = profileViewModel.sortState.value.ordinal
        )
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
    }

    private fun initUi() {
        binding.rvFilterOptions.adapter = filterOptionsAdapter
    }

    private fun setupListeners() {
        binding.btnSaveFilterOption.setOnClickListener {
            profileViewModel.saveSortState(filterOptionsAdapter.getCurrentSelectedItem())
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
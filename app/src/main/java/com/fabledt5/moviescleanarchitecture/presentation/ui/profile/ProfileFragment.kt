package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentProfileBinding
import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.presentation.adapters.pagers.ProfilePagerAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val profileViewModel by viewModels<ProfileViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private val binding: FragmentProfileBinding by viewBinding()

    private val tabChangedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab?.position == 2) binding.ivChangeListShape.isVisible = false
            tab?.let { profileViewModel.changeTab(tabPosition = it.position) }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            if (tab?.position == 2) binding.ivChangeListShape.isVisible = true
        }

        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    private val imageCropper = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful && result.uriContent != null) {
            profileViewModel.updateUserImage(getCapturedImage(result.uriContent!!))
        }
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
        initPager()
        setupObservers()
    }

    private fun initUi() {
        binding.profileTabs.addOnTabSelectedListener(tabChangedListener)
    }

    private fun setupListeners() = with(binding) {
        ivFilterList.setOnClickListener { findNavController().navigate(R.id.openFilterDialog) }

        ivChangeListShape.setOnClickListener { profileViewModel.changeListsShape() }
        ivReverseList.setOnClickListener { profileViewModel.toggleListReverse() }

        ivProfilePicture.setOnClickListener {
            imageCropper.launch(
                options {
                    setCropShape(CropImageView.CropShape.OVAL)
                    setGuidelines(CropImageView.Guidelines.OFF)
                }
            )
        }
    }

    private fun initPager() {
        binding.vpProfilePager.adapter = ProfilePagerAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(binding.profileTabs, binding.vpProfilePager) { tab, index ->
            when (index) {
                0 -> tab.text = getString(R.string.want)
                1 -> tab.text = getString(R.string.watched)
                2 -> tab.text = getString(R.string.people)
            }
        }.attach()

        binding.vpProfilePager.isUserInputEnabled = false
    }

    private fun setupObservers() {
        profileViewModel.sortState.onEach {
            binding.tvSortedBy.text = String.format(getString(R.string.sorted_by), it.sortName)
        }.launchWhenStarted(lifecycleScope)

        profileViewModel.listsShape.onEach {
            binding.ivChangeListShape.setImageResource(
                when (it) {
                    MovieListShape.SMALL -> R.drawable.ic_list_small
                    MovieListShape.MEDIUM -> R.drawable.ic_list_medium
                    MovieListShape.BIG -> R.drawable.ic_list_big
                }
            )
        }.launchWhenStarted(lifecycleScope)

        profileViewModel.isListsReverted.onEach { isReversed ->
            binding.ivReverseList.setImageResource(
                if (!isReversed) R.drawable.ic_list_order
                else R.drawable.ic_list_order_reversed
            )
        }.launchWhenStarted(lifecycleScope)

        profileViewModel.contentSizeState.onEach { size ->
            binding.tvContentCounter.text = String.format(
                if (binding.profileTabs.selectedTabPosition != 2) getString(R.string.movies_counter)
                else getString(R.string.persons_counter), size
            )
        }.launchWhenStarted(lifecycleScope)

        profileViewModel.userImage.onEach { image ->
            binding.ivProfilePicture.load(image) {
                error(R.drawable.ic_profile)
                fallback(R.drawable.ic_profile)
            }
        }.launchWhenStarted(lifecycleScope)
    }

    @Suppress("DEPRECATION")
    private fun getCapturedImage(uri: Uri) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
    }

}
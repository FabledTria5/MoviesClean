package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.transition.TransitionInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentSettingsBinding
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.presentation.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val profileViewModel by viewModels<ProfileViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private val binding: FragmentSettingsBinding by viewBinding()

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful && result.uriContent != null) {
            profileViewModel.updateUserImage(getCapturedImage(result.uriContent!!))
        } else Timber.e(result.error)
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() = with(binding) {
        tvChangeName.setOnClickListener {
            etUserName.isEnabled = true
            etUserName.setSelection(etUserName.text.length)
            etUserName.requestFocus()
            showKeyboard(etUserName)
        }

        etUserName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                etUserName.isEnabled = false
                etUserName.clearFocus()
                profileViewModel.updateUserName(binding.etUserName.text.toString())
                hideKeyboard()
            }
            return@setOnEditorActionListener true
        }

        ivProfileImage.setOnClickListener {
            cropImage.launch(
                options {
                    setCropShape(cropShape = CropImageView.CropShape.OVAL)
                    setGuidelines(guidelines = CropImageView.Guidelines.OFF)
                }
            )
        }
    }

    private fun setupObservers() {
        profileViewModel.userImage.onEach { image ->
            binding.ivProfileImage.load(image) {
                error(R.drawable.ic_profile)
                fallback(R.drawable.ic_profile)
                listener(
                    onSuccess = { _, _ -> startPostponedEnterTransition() },
                    onError = { _, _ -> startPostponedEnterTransition() })
            }
        }.launchIn(lifecycleScope)

        profileViewModel.userName.onEach { userName ->
            binding.etUserName.setText(userName)
        }.launchWhenStarted(lifecycleScope)
    }

    private fun getCapturedImage(uri: Uri) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
    }

}
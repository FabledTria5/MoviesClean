package com.fabledt5.moviescleanarchitecture.presentation.ui.person

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.GrayscaleTransformation
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentPersonBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.PersonMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import com.fabledt5.moviescleanarchitecture.presentation.utils.rotate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PersonFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var personViewModelFactory: PersonViewmodel.PersonViewmodelFactory.Factory

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    private val navArgs: PersonFragmentArgs by navArgs()

    private val personViewModel: PersonViewmodel by viewModels {
        personViewModelFactory.create(personId = navArgs.personId)
    }

    private val expandBiographyListener = View.OnClickListener {
        with(binding) {
            if (tvPersonBiography.isExpanded) tvPersonBiography.collapse()
            else tvPersonBiography.expand()
            ivShowMoreText.rotate(angle = 180f)
        }
    }

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            PersonFragmentDirections.actionPersonFragmentToMovieFragment(movieId, moviePoster)
                .also { findNavController().navigate(it) }
        }
    }

    private val personMoviesListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PersonMoviesListAdapter(onMovieClickListener)
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
        observePerson()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUi() {
        binding.ivPersonPhoto.load(navArgs.personImage) {
            transformations(GrayscaleTransformation())
            error(R.drawable.person_placeholder)
        }

        binding.rvKnownFor.layoutManager = GridLayoutManager(context, 3)
        binding.rvKnownFor.adapter = personMoviesListAdapter
    }

    private fun setupListeners() {
        binding.ivAddToFavorite.setOnClickListener {
            personViewModel.savePerson()
        }

        binding.ivShowMoreText.setOnClickListener(expandBiographyListener)
    }

    private fun observePerson() {
        personViewModel.personDetails.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showPersonLoadingError()
                    Timber.e(result.message)
                }
                is Resource.Success -> {
                    setPersonInfo(result.data)
                    delay(timeMillis = 500)
                    personViewModel.loadAdditionalInfo()
                }
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)

        personViewModel.isFavoritePerson.onEach { isFavorite ->
            binding.ivAddToFavorite.setImageResource(
                if (isFavorite)
                    R.drawable.ic_favorite_selected
                else
                    R.drawable.ic_favorite_unselected
            )
        }.launchWhenStarted(lifecycleScope)

        personViewModel.personCredits.onEach { result ->
            when (result) {
                is Resource.Error -> {
                    showCreditsLoadingError()
                    Timber.e(result.message)
                }
                is Resource.Success -> showPersonCredits(result.data)
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun setPersonInfo(personData: PersonItem?) = with(binding) {
        personData?.let { person ->
            tvPersonName.text = person.personName
            tvPersonBiography.text = person.personBiography
            tvPersonBirthday.text = person.personBirthday
            tvPersonCountry.text = person.personPlaceOfBirth
        }
        personLoadingIndicator.hide()
        groupMainContent.isVisible = true

        ivShowMoreText.isVisible = personData?.personBiography?.isNotEmpty() == true
    }

    private fun showPersonCredits(credits: List<MovieItem>?) {
        personMoviesListAdapter.submitList(credits)
        binding.personCreditsLoadingIndicator.hide()
        binding.rvKnownFor.animateAlpha(duration = 200, targetAlpha = 1f)
    }

    private fun showPersonLoadingError() = with(binding) {
        personLoadingIndicator.hide()
        tvPersonErrorMessage.animateAlpha(duration = 200, targetAlpha = 1f)
    }

    private fun showCreditsLoadingError() = with(binding) {
        personLoadingIndicator.hide()
        rvKnownFor.visibility = View.GONE
        materialTextView2.visibility = View.GONE
    }
}
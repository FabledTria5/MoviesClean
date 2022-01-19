package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentProfilePeopleBinding
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.FavoritePeopleListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.animateAlpha
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.launchWhenStarted
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfilePeopleFragment : Fragment(R.layout.fragment_profile_people) {

    @Inject
    lateinit var multiViewModelFactory: MultiViewModelFactory

    private val profileViewModel by viewModels<ProfileViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { multiViewModelFactory }
    )

    private val binding: FragmentProfilePeopleBinding by viewBinding()

    private val onPersonClickListener = object : OnPersonClickListener {
        override fun onPersonClick(personId: Int, personImage: String?) {
            ProfileFragmentDirections.actionProfileFragmentToPersonFragment(personId, personImage)
                .also { direction ->
                    findNavController().navigate(direction)
                }
        }
    }

    private val onDeletePersonClickedListener: (Int) -> Unit = { profileViewModel.deletePerson(it) }

    private val favoritePeopleListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoritePeopleListAdapter(
            onPersonClickListener = onPersonClickListener,
            onDeletePersonClicked = onDeletePersonClickedListener
        )
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        observePeople()
    }

    private fun initUi() {
        binding.rvFavoritePeople.adapter = favoritePeopleListAdapter
    }

    private fun observePeople() {
        profileViewModel.favoritePersons.onEach { result ->
            when (result) {
                is Resource.Error -> Timber.e(result.message)
                is Resource.Loading -> Timber.d("loading")
                is Resource.Success -> showPersons(result.data)
                else -> Unit
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showPersons(personsList: List<PersonItem>?) {
        favoritePeopleListAdapter.submitList(personsList) {
            binding.rvFavoritePeople.animateAlpha(targetAlpha = 1f)
            binding.rvFavoritePeople.scrollToPosition(0)
        }
    }
}
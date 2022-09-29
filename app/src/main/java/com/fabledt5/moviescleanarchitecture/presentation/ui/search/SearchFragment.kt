package com.fabledt5.moviescleanarchitecture.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentSearchBinding
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnMovieClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.listeners.OnPersonClickListener
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.SearchMoviesListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.adapters.lists.SearchPersonsListAdapter
import com.fabledt5.moviescleanarchitecture.presentation.utils.MultiViewModelFactory
import com.fabledt5.moviescleanarchitecture.presentation.utils.applicationComponent
import com.fabledt5.moviescleanarchitecture.presentation.utils.hide
import com.fabledt5.moviescleanarchitecture.presentation.utils.hideKeyboard
import com.fabledt5.moviescleanarchitecture.presentation.utils.setDrawableDivider
import com.fabledt5.moviescleanarchitecture.presentation.utils.show
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    private val searchViewModel by viewModels<SearchViewModel>(
        ownerProducer = { activity as MainActivity },
        factoryProducer = { viewModelFactory }
    )

    private val binding: FragmentSearchBinding by viewBinding()

    private val onMovieClickListener = object : OnMovieClickListener {
        override fun onMovieClick(movieId: Int, moviePoster: String?) {
            SearchFragmentDirections.actionOpenMovie(movieId, moviePoster).also { direction ->
                findNavController().navigate(direction)
            }
        }
    }

    private val onPersonClickListener = object : OnPersonClickListener {
        override fun onPersonClick(personId: Int, personImage: String?) {
            SearchFragmentDirections.actionOpenPerson(personId, personImage).also { direction ->
                findNavController().navigate(direction)
            }
        }
    }

    private val moviesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchMoviesListAdapter(onMovieClickListener)
    }

    private val personsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchPersonsListAdapter(onPersonClickListener)
    }

    override fun onAttach(context: Context) {
        context.applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        setupListeners()
        observeData()

        binding.tieSearchView.setText(searchViewModel.currentSearchQuery.value)
    }

    private fun initUi() {
        binding.rvMoviesList.apply {
            adapter = moviesAdapter
            setDrawableDivider(R.drawable.lists_divider)
        }

        binding.rvPersonsList.apply {
            adapter = personsAdapter
            setDrawableDivider(R.drawable.lists_divider)
        }
    }

    private fun setupListeners() {
        binding.tilSearchView.setStartIconOnClickListener {
            searchViewModel.currentSearchQuery.value = binding.tieSearchView.text.toString()
            hideKeyboard()
        }

        binding.tieSearchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.currentSearchQuery.value = binding.tieSearchView.text.toString()
                hideKeyboard()
            }
            true
        }

        moviesAdapter.addLoadStateListener {
            if (moviesAdapter.itemCount < 1) {
                binding.tvMoviesLabel.hide()
                binding.rvMoviesList.hide()
            } else {
                binding.tvMoviesLabel.show()
                binding.rvMoviesList.show()
            }
        }

        personsAdapter.addLoadStateListener {
            if (personsAdapter.itemCount < 1) {
                binding.tvPersonsLabel.hide()
                binding.rvPersonsList.hide()
            } else {
                binding.tvPersonsLabel.show()
                binding.rvPersonsList.show()
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.moviesList.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            searchViewModel.personsList.collectLatest {
                personsAdapter.submitData(it)
            }
        }
    }

}
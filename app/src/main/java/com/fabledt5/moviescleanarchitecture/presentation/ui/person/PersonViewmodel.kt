package com.fabledt5.moviescleanarchitecture.presentation.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.Resource
import com.fabledt5.moviescleanarchitecture.domain.use_case.person_details.GetPersonCredits
import com.fabledt5.moviescleanarchitecture.domain.use_case.person_details.GetPersonDetails
import com.fabledt5.moviescleanarchitecture.domain.use_case.person_details.IsPersonFavorite
import com.fabledt5.moviescleanarchitecture.domain.use_case.person_details.SavePerson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PersonViewmodel(
    getPersonDetails: GetPersonDetails,
    private val getPersonCredits: GetPersonCredits,
    private val personId: Int,
    private val savePerson: SavePerson,
    private val isPersonFavorite: IsPersonFavorite,
) : ViewModel() {

    private val _personDetails = MutableStateFlow<Resource<PersonItem>>(Resource.Loading)
    val personDetails = _personDetails.asStateFlow()

    private val _personCredits = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val personCredits = _personCredits.asStateFlow()

    private val _isFavoritePerson = MutableStateFlow(false)
    val isFavoritePerson = _isFavoritePerson.asStateFlow()

    init {
        getPersonDetails(personId = personId).onEach { personItem ->
            _personDetails.value = personItem
        }.launchIn(viewModelScope)
    }

    fun loadAdditionalInfo() {
        getPersonCredits(personId = personId).onEach { personCredits ->
            _personCredits.value = personCredits
        }.launchIn(viewModelScope)

        isPersonFavorite(personId = personId).onEach {
            _isFavoritePerson.value = it
        }.launchIn(viewModelScope)
    }

    fun savePerson() = viewModelScope.launch {
        if (!_isFavoritePerson.value)
            (_personDetails.value as Resource.Success).let {
                savePerson(it.data)
            }
    }

    class PersonViewmodelFactory @AssistedInject constructor(
        @Assisted(value = "personId") private val personId: Int,
        private val getPersonDetails: GetPersonDetails,
        private val getPersonCredits: GetPersonCredits,
        private val savePerson: SavePerson,
        private val isPersonFavorite: IsPersonFavorite,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PersonViewmodel(
                getPersonDetails,
                getPersonCredits,
                personId,
                savePerson,
                isPersonFavorite
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(@Assisted(value = "personId") personId: Int): PersonViewmodelFactory
        }

    }

}
package com.fabledt5.moviescleanarchitecture.presentation.ui.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabledt5.moviescleanarchitecture.domain.model.*
import com.fabledt5.moviescleanarchitecture.domain.model.items.MovieItem
import com.fabledt5.moviescleanarchitecture.domain.model.items.PersonItem
import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState
import com.fabledt5.moviescleanarchitecture.domain.use_case.profile.favorites.FavoritesUseCasesWrapper
import com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences.*
import com.fabledt5.moviescleanarchitecture.domain.use_case.profile.settings.*
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    readSortState: ReadSortState,
    readListsShape: ReadListsShape,
    private val settingsUseCasesWrapper: SettingsUseCasesWrapper,
    private val preferencesUseCasesWrapper: PreferencesUseCasesWrapper,
    private val favoritesUseCasesWrapper: FavoritesUseCasesWrapper
) : ViewModel() {

    private val _wantedMovies = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val wantedMovies = _wantedMovies.asStateFlow()

    private val _watchedMovies = MutableStateFlow<Resource<List<MovieItem>>>(Resource.Loading)
    val watchedMovies = _watchedMovies.asStateFlow()

    private val _favoritePersons = MutableStateFlow<Resource<List<PersonItem>>>(Resource.Loading)
    val favoritePersons = _favoritePersons.asStateFlow()

    private val _userImage = MutableStateFlow<Bitmap?>(value = null)
    val userImage = _userImage.asStateFlow()

    private val _userName = MutableStateFlow(value = "")
    val userName = _userName.asStateFlow()

    val contentSizeState = MutableStateFlow(0)
    val sortState = MutableStateFlow(SortState.DATE)
    val listsShape = MutableStateFlow(MovieListShape.BIG)
    val isListsReverted = MutableStateFlow(false)

    private var selectedContentPage = 0

    init {
        readSortState().onEach {
            sortState.value = it
            getMovesList()
        }.launchIn(viewModelScope)

        readListsShape().onEach { shape ->
            listsShape.value = shape
        }.launchIn(viewModelScope)

        settingsUseCasesWrapper.readUserImage().onEach { image ->
            if (image == null) _userImage.value = image
            else {
                val bytes = Base64.decode(image.encodeToByteArray(), Base64.DEFAULT)
                _userImage.value = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }.launchIn(viewModelScope)

        settingsUseCasesWrapper.readUserName().onEach { userName ->
            _userName.value = userName
        }.launchIn(viewModelScope)

        getPeopleList()
    }

    fun toggleListReverse() {
        isListsReverted.value = !isListsReverted.value
        getMovesList()
        getPeopleList()
    }

    fun changeTab(tabPosition: Int) {
        selectedContentPage = tabPosition
        contentSizeState.value = when (tabPosition) {
            0 -> (_wantedMovies.value as? Resource.Success)?.data?.size ?: 0
            1 -> (_watchedMovies.value as? Resource.Success)?.data?.size ?: 0
            2 -> (_favoritePersons.value as? Resource.Success)?.data?.size ?: 0
            else -> 0
        }
    }

    fun changeListsShape() = viewModelScope.launch(Dispatchers.IO) {
        preferencesUseCasesWrapper.updateListsShape(listsShape.value)
    }

    fun saveSortState(sortState: SortState) = viewModelScope.launch(Dispatchers.IO) {
        preferencesUseCasesWrapper.updateSortState(sortState)
    }

    fun deleteMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCasesWrapper.deleteMovie(movieId = movieId)
    }

    fun deletePerson(personId: Int) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCasesWrapper.deletePerson(personId = personId)
    }

    fun updateUserImage(userImage: Bitmap) = viewModelScope.launch(Dispatchers.IO) {
        val outputSteam = ByteArrayOutputStream()
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, outputSteam)
        val bytes = outputSteam.toByteArray()
        val encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT)
        settingsUseCasesWrapper.updateUserImage(encodedImage)
    }

    fun updateUserName(userName: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsUseCasesWrapper.updateUserName(name = userName)
    }

    private fun getMovesList() {
        favoritesUseCasesWrapper.getWantedMovies(
            movieType = MovieType.WANT,
            sortState = sortState.value,
            isReversed = isListsReverted.value
        ).onEach { result ->
            _wantedMovies.value = if (result.isNullOrEmpty()) {
                if (selectedContentPage == 0) contentSizeState.value = 0
                Resource.Error(message = "Empty List")
            } else {
                if (selectedContentPage == 0) contentSizeState.value = result.size
                Resource.Success(data = result)
            }
        }.launchIn(viewModelScope)

        favoritesUseCasesWrapper.getWatchedMovies(
            movieType = MovieType.WATCHED,
            sortState = sortState.value,
            isReversed = isListsReverted.value
        ).onEach { result ->
            _watchedMovies.value = if (result.isNullOrEmpty()) {
                if (selectedContentPage == 1) contentSizeState.value = 0
                Resource.Error(message = "Empty List")
            } else {
                if (selectedContentPage == 1) contentSizeState.value = result.size
                Resource.Success(data = result)
            }
        }.launchIn(viewModelScope)
    }

    private fun getPeopleList() {
        favoritesUseCasesWrapper.getFavoritePeople(
            isReversed = isListsReverted.value
        ).onEach { result ->
            _favoritePersons.value = if (result.isNullOrEmpty()) {
                if (selectedContentPage == 2) contentSizeState.value = 0
                Resource.Error(message = "Empty list")
            } else {
                if (selectedContentPage == 2) contentSizeState.value = result.size
                Resource.Success(data = result)
            }
        }.launchIn(viewModelScope)
    }

}
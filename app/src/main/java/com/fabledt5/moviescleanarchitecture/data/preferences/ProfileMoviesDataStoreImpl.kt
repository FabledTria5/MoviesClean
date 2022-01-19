package com.fabledt5.moviescleanarchitecture.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.fabledt5.moviescleanarchitecture.domain.model.states.MovieListShape
import com.fabledt5.moviescleanarchitecture.domain.model.states.SortState
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.LISTS_SHAPE_KEY
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.PREFERENCE_NAME
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.SORT_PREFERENCE_KEY
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.USER_IMAGE_PREFERENCE_KEY
import com.fabledt5.moviescleanarchitecture.presentation.utils.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ProfileMoviesDataStoreImpl @Inject constructor(
    context: Context,
) : ProfileMoviesDataStoreRepository {

    private object PreferencesKeys {
        val sortKey = stringPreferencesKey(name = SORT_PREFERENCE_KEY)
        val listsShapeKey = intPreferencesKey(name = LISTS_SHAPE_KEY)
    }

    private val dataStore = context.datastore

    override suspend fun persistSortState(sortState: SortState) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.sortKey] = sortState.name
        }
    }

    override suspend fun persistListShape(listsShape: Int) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.listsShapeKey] = listsShape
        }
    }

    override val readListsShape: Flow<MovieListShape> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { preference ->
            val shape = preference[PreferencesKeys.listsShapeKey] ?: 0
            enumValues<MovieListShape>()[shape]
        }

    override val readSortState: Flow<SortState> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { preference ->
            val sortState = preference[PreferencesKeys.sortKey] ?: SortState.DATE.name
            SortState.valueOf(sortState)
        }

}
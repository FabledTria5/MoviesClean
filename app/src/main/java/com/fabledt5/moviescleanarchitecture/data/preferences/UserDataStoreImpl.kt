package com.fabledt5.moviescleanarchitecture.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import com.fabledt5.moviescleanarchitecture.domain.util.Constants
import com.fabledt5.moviescleanarchitecture.presentation.utils.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor(context: Context) : UserDataStoreRepository {

    private object PreferencesKeys {
        val userImageKey = stringPreferencesKey(name = Constants.USER_IMAGE_PREFERENCE_KEY)
        val userNameKey = stringPreferencesKey(name = Constants.USER_NAME_PREFERENCE_KEY)
    }

    private val dataStore = context.datastore

    override suspend fun persistUserImage(codedImage: String) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.userImageKey] = codedImage
        }
    }

    override suspend fun persistUserName(name: String) {
        dataStore.edit { preference ->
            preference[PreferencesKeys.userNameKey] = name
        }
    }

    override val readUserImage: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { preference ->
            preference[PreferencesKeys.userImageKey]
        }

    override val readUserName: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }.map { preference ->
            preference[PreferencesKeys.userNameKey]
        }

}
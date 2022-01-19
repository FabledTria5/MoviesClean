package com.fabledt5.moviescleanarchitecture

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.fabledt5.moviescleanarchitecture.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class MoviesApplication : Application() {

    val appComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.builder()
            .withContext(applicationContext)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}
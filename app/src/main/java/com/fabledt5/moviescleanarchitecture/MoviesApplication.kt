package com.fabledt5.moviescleanarchitecture

import android.app.Application
import com.fabledt5.moviescleanarchitecture.di.DaggerAppComponent
import timber.log.Timber

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
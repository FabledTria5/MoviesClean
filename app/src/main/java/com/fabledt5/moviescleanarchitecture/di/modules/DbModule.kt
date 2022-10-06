package com.fabledt5.moviescleanarchitecture.di.modules

import android.content.Context
import androidx.room.Room
import com.fabledt5.moviescleanarchitecture.data.db.MoviesDatabase
import com.fabledt5.moviescleanarchitecture.data.db.dao.MoviesDao
import com.fabledt5.moviescleanarchitecture.domain.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase = Room
        .databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun provideDao(moviesDatabase: MoviesDatabase): MoviesDao = moviesDatabase.moviesDao()

}
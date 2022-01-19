package com.fabledt5.moviescleanarchitecture.di.modules

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(includes = [NetworkModule::class, DbModule::class, AppBindsModule::class])
object AppModule {
}
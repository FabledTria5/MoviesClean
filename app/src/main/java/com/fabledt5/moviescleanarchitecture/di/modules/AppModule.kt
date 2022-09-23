package com.fabledt5.moviescleanarchitecture.di.modules

import dagger.Module

@Module(includes = [NetworkModule::class, DbModule::class, AppBindsModule::class])
object AppModule {
}
package com.fabledt5.moviescleanarchitecture.di.modules

import com.fabledt5.moviescleanarchitecture.BuildConfig
import com.fabledt5.moviescleanarchitecture.data.api.ApiInterceptor
import com.fabledt5.moviescleanarchitecture.data.api.MoviesApi
import com.fabledt5.moviescleanarchitecture.domain.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }
        interceptors.add(loggingInterceptor)
        interceptors.add(ApiInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideClient(interceptors: ArrayList<Interceptor>): OkHttpClient =
        OkHttpClient.Builder().followRedirects(false).also { client ->
            interceptors.forEach { client.addInterceptor(it) }
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.API_BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

}
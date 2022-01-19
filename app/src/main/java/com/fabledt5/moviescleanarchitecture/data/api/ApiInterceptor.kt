package com.fabledt5.moviescleanarchitecture.data.api

import com.fabledt5.moviescleanarchitecture.domain.util.Constants.API_KEY
import okhttp3.Interceptor

object ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder().url(
            chain.request().url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        ).build()
    )

}
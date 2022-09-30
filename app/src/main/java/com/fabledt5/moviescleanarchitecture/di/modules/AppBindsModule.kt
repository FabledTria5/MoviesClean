@file:Suppress("unused")

package com.fabledt5.moviescleanarchitecture.di.modules

import androidx.lifecycle.ViewModel
import com.fabledt5.moviescleanarchitecture.data.preferences.ProfileMoviesDataStoreImpl
import com.fabledt5.moviescleanarchitecture.data.preferences.UserDataStoreImpl
import com.fabledt5.moviescleanarchitecture.data.repository.DetailsRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.ExploreRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.ProfileRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.SearchRepositoryImpl
import com.fabledt5.moviescleanarchitecture.di.keys.ViewModelKey
import com.fabledt5.moviescleanarchitecture.domain.repository.DetailsRepository
import com.fabledt5.moviescleanarchitecture.domain.repository.ExploreRepository
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileMoviesDataStoreRepository
import com.fabledt5.moviescleanarchitecture.domain.repository.ProfileRepository
import com.fabledt5.moviescleanarchitecture.domain.repository.SearchRepository
import com.fabledt5.moviescleanarchitecture.domain.repository.UserDataStoreRepository
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.HomeViewModel
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfileViewModel
import com.fabledt5.moviescleanarchitecture.presentation.ui.search.SearchViewModel
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatusListener
import com.fabledt5.moviescleanarchitecture.presentation.utils.network.NetworkStatusListenerImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface AppBindsModule {

    @Binds
    @Singleton
    fun bindExploreRepository(exploreRepositoryImpl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    @Singleton
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    fun bindProfileMoviesDataStoreRepository(profileMoviesData: ProfileMoviesDataStoreImpl):
            ProfileMoviesDataStoreRepository

    @Binds
    @Singleton
    fun bindUserDataStoreRepository(userDataStoreImpl: UserDataStoreImpl): UserDataStoreRepository

    @Binds
    @Singleton
    fun bindNetworkListener(networkStatusListenerImpl: NetworkStatusListenerImpl): NetworkStatusListener

    @Binds
    @[IntoMap ViewModelKey(HomeViewModel::class)]
    fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SearchViewModel::class)]
    fun provideSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ProfileViewModel::class)]
    fun provideProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

}
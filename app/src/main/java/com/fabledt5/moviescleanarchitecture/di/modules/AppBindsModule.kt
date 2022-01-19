package com.fabledt5.moviescleanarchitecture.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.fabledt5.moviescleanarchitecture.data.preferences.ProfileMoviesDataStoreImpl
import com.fabledt5.moviescleanarchitecture.data.preferences.UserDataStoreImpl
import com.fabledt5.moviescleanarchitecture.data.repository.DetailsRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.ExploreRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.ProfileRepositoryImpl
import com.fabledt5.moviescleanarchitecture.data.repository.SearchRepositoryImpl
import com.fabledt5.moviescleanarchitecture.di.keys.ViewModelKey
import com.fabledt5.moviescleanarchitecture.domain.repository.*
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.HomeViewModel
import com.fabledt5.moviescleanarchitecture.presentation.ui.movie.MovieViewModel
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfileViewModel
import com.fabledt5.moviescleanarchitecture.presentation.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface AppBindsModule {

    @Binds
    fun bindExploreRepository(exploreRepositoryImpl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindProfileMoviesDataStoreRepository(profileMoviesData: ProfileMoviesDataStoreImpl):
            ProfileMoviesDataStoreRepository

    @Binds
    fun bindUserDataStoreRepository(userDataStoreImpl: UserDataStoreImpl): UserDataStoreRepository

    @Binds
    @[IntoMap ViewModelKey(HomeViewModel::class)]
    fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SearchViewModel::class)]
    fun provideSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ProfileViewModel::class)]
    fun provideProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(MovieViewModel::class)]
    fun provideMovieViewModel(movieViewModel: MovieViewModel): ViewModel

}
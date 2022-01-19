package com.fabledt5.moviescleanarchitecture.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.fabledt5.moviescleanarchitecture.di.modules.AppModule
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.HomeFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.MoviesFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.movie.MovieFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.person.PersonFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.*
import com.fabledt5.moviescleanarchitecture.presentation.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(MoviesFragment: MoviesFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(searchFragment: SearchFragment)

    fun inject(movieFragment: MovieFragment)

    fun inject(personFragment: PersonFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(profileMoviesFragment: ProfileMoviesFragment)

    fun inject(filterFragment: FilterFragment)

    fun inject(profilePeopleFragment: ProfilePeopleFragment)

    fun inject(settingsFragment: SettingsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        fun build(): AppComponent
    }

}
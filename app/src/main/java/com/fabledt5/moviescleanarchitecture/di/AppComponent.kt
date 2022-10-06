package com.fabledt5.moviescleanarchitecture.di

import android.content.Context
import com.fabledt5.moviescleanarchitecture.MainActivity
import com.fabledt5.moviescleanarchitecture.di.modules.AppModule
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.HomeFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.MoviesFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.home.SelectionsFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.movie.MovieFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.person.PersonFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.FilterFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfileFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfileMoviesFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.profile.ProfilePeopleFragment
import com.fabledt5.moviescleanarchitecture.presentation.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(moviesFragment: MoviesFragment)

    fun inject(selectionsFragment: SelectionsFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(movieFragment: MovieFragment)

    fun inject(personFragment: PersonFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(profileMoviesFragment: ProfileMoviesFragment)

    fun inject(filterFragment: FilterFragment)

    fun inject(profilePeopleFragment: ProfilePeopleFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withContext(context: Context): Builder

        fun build(): AppComponent
    }

}
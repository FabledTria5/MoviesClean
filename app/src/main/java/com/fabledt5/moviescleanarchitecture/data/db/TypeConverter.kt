package com.fabledt5.moviescleanarchitecture.data.db

import androidx.room.TypeConverter
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType

object TypeConverter {

    @TypeConverter
    fun fromMovieType(movieType: MovieType) = movieType.ordinal

    @TypeConverter
    fun toMovieType(movieTypeOrdinal: Int) = enumValues<MovieType>()[movieTypeOrdinal]

}
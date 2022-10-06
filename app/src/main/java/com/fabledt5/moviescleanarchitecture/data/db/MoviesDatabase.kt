package com.fabledt5.moviescleanarchitecture.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fabledt5.moviescleanarchitecture.data.db.dao.MoviesDao
import com.fabledt5.moviescleanarchitecture.data.db.entity.MovieEntity
import com.fabledt5.moviescleanarchitecture.data.db.entity.PersonEntity

@Database(
    entities = [MovieEntity::class, PersonEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
package com.fabledt5.moviescleanarchitecture.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType

@Entity(tableName = "movies_table")
data class MovieEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "movie_type")
    val movieType: MovieType,
    @ColumnInfo(name = "movie_poster")
    val moviePoster: String? = null,
    @ColumnInfo(name = "movie_rating")
    val movieRating: Float = 0f,
    @ColumnInfo(name = "movie_title")
    val movieTitle: String = "",
    @ColumnInfo(name = "movie_release")
    val movieRelease: String = "",
    @ColumnInfo(name = "movie_country")
    val movieCountry: String = "",
    @ColumnInfo(name = "movie_runtime")
    val movieRuntime: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
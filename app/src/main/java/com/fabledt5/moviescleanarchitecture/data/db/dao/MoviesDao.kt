package com.fabledt5.moviescleanarchitecture.data.db.dao

import androidx.room.*
import com.fabledt5.moviescleanarchitecture.data.db.entity.MovieEntity
import com.fabledt5.moviescleanarchitecture.data.db.entity.PersonEntity
import com.fabledt5.moviescleanarchitecture.domain.util.MovieType
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personEntity: PersonEntity)

    @Query(value = "DELETE FROM movies_table WHERE movie_id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query(value = "DELETE FROM persons_table WHERE person_id = :personId")
    suspend fun deletePerson(personId: Int)

    @Query(value = "SELECT movie_type FROM movies_table WHERE movie_id = :movieId")
    fun getMovieType(movieId: Int): Flow<MovieType>

    @Query(value = "SELECT id FROM movies_table WHERE movie_title = :movieTitle")
    suspend fun getMovieId(movieTitle: String): Int?

    @Query(value = "SELECT EXISTS(SELECT * FROM persons_table WHERE person_id = :personId)")
    fun isPersonFavorite(personId: Int): Flow<Boolean>

    @Query(value = "SELECT * FROM movies_table WHERE movie_type = :movieType")
    fun getMoviesList(movieType: Int): Flow<List<MovieEntity>>

    @Query(value = "SELECT * FROM persons_table")
    fun getAllPersons(): Flow<List<PersonEntity>>
}
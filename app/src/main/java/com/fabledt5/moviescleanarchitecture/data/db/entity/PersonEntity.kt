package com.fabledt5.moviescleanarchitecture.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons_table")
data class PersonEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "person_name")
    val personName: String,
    @ColumnInfo(name = "place_of_birth")
    val placeOfBirth: String,
    @ColumnInfo(name = "person_image")
    val personImage: String?
)

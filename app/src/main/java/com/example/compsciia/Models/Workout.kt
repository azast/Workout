package com.example.compsciia.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workouts_table")

data class Workout(@PrimaryKey(autoGenerate = true) val id: Int?, @ColumnInfo(name = "title") val title: String?, @ColumnInfo(name = "workout") val workout: String?, @ColumnInfo(name = "date") val date: String?): java.io.Serializable


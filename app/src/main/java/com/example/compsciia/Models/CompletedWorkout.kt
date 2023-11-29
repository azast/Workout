package com.example.compsciia.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "completed_table")
data class CompletedWorkout(
    @PrimaryKey(autoGenerate = true) val id2: Int?,
    @ColumnInfo(name = "title") val title2: String?,
    @ColumnInfo(name = "date") val date2: String?,
    @ColumnInfo(name = "associatedWorkoutId") val associatedWorkoutId: Int?
) : java.io.Serializable

package com.example.compsciia.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compsciia.Models.Workout


@Dao
interface WorkoutDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)



    @Query("Select * from workouts_table order by id ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>


    @Query("UPDATE workouts_table Set title= :title, workout = :workout WHERE id= :id ")
    suspend fun update(id: Int?, title: String?, workout: String?)
}
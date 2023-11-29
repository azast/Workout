package com.example.compsciia.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compsciia.Models.CompletedWorkout
import com.example.compsciia.Models.Workout



@Dao
interface CompletedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompleted(completed: CompletedWorkout)

    @Delete
    suspend fun deleteCompleted(completed: CompletedWorkout)

    //@Query("Select * from completed_table order by id2 ASC")
    //fun getAllCompletedWorkouts(): LiveData<List<CompletedWorkout>>

    @Query("SELECT * FROM completed_table ORDER BY id2 DESC")
    fun getAllCompletedWorkouts(): LiveData<List<CompletedWorkout>>

}
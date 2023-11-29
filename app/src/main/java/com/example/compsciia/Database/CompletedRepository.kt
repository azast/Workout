package com.example.compsciia.Database

import androidx.lifecycle.LiveData
import com.example.compsciia.Models.CompletedWorkout
import com.example.compsciia.Models.Workout

class CompletedRepository(private val completedDao: CompletedDao) {

    val allCompletedWorkouts: LiveData<List<CompletedWorkout>> = completedDao.getAllCompletedWorkouts()

    suspend fun insert(completedWorkout: CompletedWorkout){
        completedDao.insertCompleted(completedWorkout)
    }
    suspend fun delete(completedWorkout: CompletedWorkout){
        completedDao.deleteCompleted(completedWorkout)
    }
}

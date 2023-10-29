package com.example.compsciia.Database

import androidx.lifecycle.LiveData
import com.example.compsciia.Models.Workout

class WorkoutsRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun insert(workout: Workout){
        workoutDao.insert(workout)
    }
    suspend fun delete(workout: Workout){
        workoutDao.delete(workout)
    }
    suspend fun update(workout: Workout){
        workoutDao.update(workout.id, workout.title, workout.workout)
    }
}
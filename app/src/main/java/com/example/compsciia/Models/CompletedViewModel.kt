package com.example.compsciia.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.compsciia.Database.CompletedDatabase
import com.example.compsciia.Database.CompletedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompletedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository2: CompletedRepository
    val allCompletedWorkouts: LiveData<List<CompletedWorkout>>

    init {
        val dao2 = CompletedDatabase.getCompletedDataBase(application).getCompletedDao()
        repository2 = CompletedRepository(dao2)
        allCompletedWorkouts = repository2.allCompletedWorkouts
    }

    fun deleteCompleted(completedWorkout: CompletedWorkout) = viewModelScope.launch(Dispatchers.IO) {
        repository2.delete(completedWorkout)
    }

    fun insertCompleted(completedWorkout: CompletedWorkout) = viewModelScope.launch(Dispatchers.IO) {
        repository2.insert(completedWorkout)
    }

    companion object {
        fun insertWorkout(completedWorkout: CompletedWorkout) {

        }
    }
}

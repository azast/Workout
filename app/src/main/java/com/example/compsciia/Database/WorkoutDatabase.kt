package com.example.compsciia.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compsciia.Models.Workout
import com.example.compsciia.utilities.DATABASE_NAME


@Database(entities = arrayOf(Workout::class), version = 1, exportSchema = false)
abstract class WorkoutDatabase: RoomDatabase() {

    abstract fun getWorkoutDao(): WorkoutDao

    companion object{
        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDataBase(context: Context): WorkoutDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, WorkoutDatabase::class.java, DATABASE_NAME).build()
                INSTANCE = instance

                instance
            }
        }
    }

}
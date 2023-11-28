package com.example.compsciia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.compsciia.Models.Workout
import com.example.compsciia.databinding.ActivityAddWorkoutBinding
import com.example.compsciia.databinding.ActivityWorkoutHistoryBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class WorkoutHistory : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutHistoryBinding

    private lateinit var workout: Workout

    private lateinit var old_workout: Workout

    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()

            }
        }
    }

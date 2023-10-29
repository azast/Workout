package com.example.compsciia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.compsciia.Models.Workout
import com.example.compsciia.databinding.ActivityAddWorkoutBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class AddWorkout : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkoutBinding

    private lateinit var workout: Workout

    private lateinit var old_workout: Workout

    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try {
            old_workout = intent.getSerializableExtra("current_workout") as Workout
            binding.etTitle.setText(old_workout.title)
            binding.etWorkout.setText(old_workout.workout)
            isUpdate = true


        } catch (e: Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val workout_desc = binding.etWorkout.text.toString()

            if(title.isNotEmpty() || workout_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM, yyy HH:mm a")

                if(isUpdate){
                    workout = Workout(old_workout.id, title, workout_desc, formatter.format(Date()))
            }
                else{
                    workout = Workout(null, title, workout_desc,formatter.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("workout", workout)
                setResult(Activity.RESULT_OK, intent)
                finish()


                }
            else{
                Toast.makeText(this@AddWorkout, "Please enter some data", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }
}
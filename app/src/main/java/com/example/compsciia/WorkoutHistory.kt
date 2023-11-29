package com.example.compsciia

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.compsciia.Adapter.CompletedAdapter
import com.example.compsciia.Models.CompletedViewModel
import com.example.compsciia.Models.CompletedWorkout
import com.example.compsciia.databinding.ActivityWorkoutHistoryBinding

class WorkoutHistory : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutHistoryBinding
    private lateinit var completedViewModel: CompletedViewModel
    private lateinit var adapter: CompletedAdapter
    private lateinit var selectedWorkout: CompletedWorkout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel and RecyclerView
        completedViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CompletedViewModel::class.java)

        binding.recyclerView2.setHasFixedSize(true)
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        adapter = CompletedAdapter(this, object : CompletedAdapter.CompletedClickListener {
            override fun onItemClicked(completedWorkout: CompletedWorkout) {
                // Handle item click if needed
            }

            override fun onLongItemClicked(completedWorkout: CompletedWorkout, cardView: CardView) {
                selectedWorkout = completedWorkout
                popUpDisplay(cardView)
            }
        })
        binding.recyclerView2.adapter = adapter

        // Observe the completed workouts and update the adapter
        completedViewModel.allCompletedWorkouts.observe(this) { completedWorkouts ->
            completedWorkouts?.let {
                adapter.updateList2(completedWorkouts)
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun popUpDisplay(cardView: CardView) {
        val popUp = PopupMenu(this, cardView)
        popUp.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete_completed -> {
                    completedViewModel.deleteCompleted(selectedWorkout)
                    true
                }
                else -> false
            }
        }
        popUp.inflate(R.menu.pop_up_menu2) // Define a menu resource for completed workouts
        popUp.show()
    }
}

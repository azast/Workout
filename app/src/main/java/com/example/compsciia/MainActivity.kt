package com.example.compsciia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.compsciia.Adapter.WorkoutsAdapter
import com.example.compsciia.AddWorkout
import com.example.compsciia.Database.WorkoutDatabase
import com.example.compsciia.Models.Workout
import com.example.compsciia.Models.WorkoutViewModel
import com.example.compsciia.R
import com.example.compsciia.WorkoutHistory
import com.example.compsciia.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    WorkoutsAdapter.workoutsClickListener,
    PopupMenu.OnMenuItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: WorkoutDatabase
    private lateinit var viewModel: WorkoutViewModel
    private lateinit var adapter: WorkoutsAdapter
    private lateinit var selectedWorkout: Workout

    private val updateWorkout =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val workout = result.data?.getSerializableExtra("workout") as? Workout
                if (workout != null) {
                    viewModel.updateWorkout(workout)
                }
            }
        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val workout = result.data?.getSerializableExtra("workout") as? Workout
                if (workout != null) {
                    viewModel.insertWorkout(workout)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing UI
        initUI()

        viewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(WorkoutViewModel::class.java)

        viewModel.allWorkouts.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = WorkoutDatabase.getDataBase(this)
    }

    private fun initUI() {
        // Set up the DrawerLayout and ActionBarDrawerToggle
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up the NavigationView item click listener
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = WorkoutsAdapter(this, this)

        binding.recyclerView.adapter = adapter

        //binding.fbAddWorkout.setOnClickListener {
            // Handle the click on FloatingActionButton for AddWorkout
            //val intent = Intent(this, AddWorkout::class.java)
            //getContent.launch(intent)
        //}

        //binding.fbWorkoutHistory.setOnClickListener {
            // Handle the click on FloatingActionButton for WorkoutHistory
            //val intent = Intent(this, WorkoutHistory::class.java)
            //getContent.launch(intent)
        //}

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterList(newText)
                }
                return true
            }
        })
    }

    override fun onItemClicked(workout: Workout) {
        val intent = Intent(this@MainActivity, AddWorkout::class.java)
        intent.putExtra("current_workout", workout)
        updateWorkout.launch(intent)
    }

    override fun onLongItemClicked(workout: Workout, cardView: CardView) {
        selectedWorkout = workout
        popUpDisplay(cardView)
    }

    private fun popUpDisplay(cardView: CardView) {
        val popUp = PopupMenu(this, cardView)
        popUp.setOnMenuItemClickListener(this@MainActivity)
        popUp.inflate(R.menu.pop_up_menu)
        popUp.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_workout) {
            viewModel.deleteWorkout(selectedWorkout)
            return true
        } else if (item?.itemId == R.id.complete_workout) {
            // Handle the click on Complete Workout
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item1 -> {
                // Handle the click on Menu Item 1
                val intent = Intent(this, AddWorkout::class.java)
                getContent.launch(intent)
                return true
            }
            R.id.menu_item2 -> {
                // Handle the click on Menu Item 2
                val intent = Intent(this, WorkoutHistory::class.java)
                getContent.launch(intent)
                return true
            }
            // Add more cases if you have additional menu items
            else -> return false
        }
    }
}

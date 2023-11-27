package com.example.compsciia


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.RoomDatabase
import com.example.compsciia.Adapter.WorkoutsAdapter
import com.example.compsciia.Database.WorkoutDatabase
import com.example.compsciia.Models.Workout
import com.example.compsciia.Models.WorkoutViewModel
import com.example.compsciia.databinding.ActivityAddWorkoutBinding
import com.example.compsciia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WorkoutsAdapter.workoutsClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: WorkoutDatabase
    lateinit var viewModel: WorkoutViewModel
    lateinit var adapter: WorkoutsAdapter
    lateinit var selectedWorkout: Workout

    private val updateWorkout =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val workout = result.data?.getSerializableExtra("workout") as? Workout
            if (workout != null){
                viewModel.updateWorkout(workout)
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Initializing UI
        initUI()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(WorkoutViewModel::class.java)

        viewModel.allWorkouts.observe(this) {list ->

            list?.let{
                adapter.updateList(list)
            }

        }

        database = WorkoutDatabase.getDataBase(this)
    }



    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = WorkoutsAdapter(this, this)

        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                val workout = result.data?.getSerializableExtra("workout") as? Workout
                if(workout != null){

                    viewModel.insertWorkout(workout)

                }
            }

        }
        binding.fbAddWorkout.setOnClickListener {
            val intent = Intent(this, AddWorkout::class.java)
            getContent.launch(intent)
        }
        binding.fbWorkoutHistory.setOnClickListener {
            val intent = Intent(this, WorkoutHistory::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null ){
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
        if(item?.itemId == R.id.delete_workout){
            viewModel.deleteWorkout(selectedWorkout)
            return true
        }
        else if(item?.itemId == R.id.complete_workout){
            return true
        }
        return false
    }

}
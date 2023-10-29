package com.example.compsciia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.compsciia.Models.Workout
import com.example.compsciia.R
import kotlin.random.Random

class WorkoutsAdapter(private val context: Context, val listener: workoutsClickListener) : RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder>() {

    private val workoutsList = ArrayList<Workout>()
    private val fullList = ArrayList<Workout>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        return WorkoutViewHolder(LayoutInflater.from(context ).inflate(R.layout.list_item, parent, false))
    }





    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentWorkout = workoutsList[position]
        holder.title.text = currentWorkout.title
        holder.title.isSelected = true


        holder.date.text = currentWorkout.date
       holder.date.isSelected = true


        holder.Workout_tv.text = currentWorkout.workout



        holder.workouts_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))



        holder.workouts_layout.setOnClickListener{
            listener.onItemClicked(workoutsList[holder.adapterPosition])
        }
        holder.workouts_layout.setOnLongClickListener{
            listener.onLongItemClicked(workoutsList[holder.adapterPosition], holder.workouts_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return workoutsList.size
    }

    fun updateList(newList: List<Workout>){
        fullList.clear()
        fullList.addAll(newList)

        workoutsList.clear()
        workoutsList.addAll(fullList)
        notifyDataSetChanged()
    }



    //Searching -> if the title contains string x or the description contains x, then it will filter
    fun filterList(search: String){
        workoutsList.clear()

        for(item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase()) == true || item.workout?.lowercase()?.contains(search.lowercase())==true){

                workoutsList.add(item)
            }
        }
        notifyDataSetChanged()
    }


    //Setting up randomcolors, 6 colors that randomly appear on the note
    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.WorkoutColor1)
        list.add(R.color.WorkoutColor2)
        list.add(R.color.WorkoutColor3)
        list.add(R.color.WorkoutColor4)
        list.add(R.color.WorkoutColor5)
        list.add(R.color.WorkoutColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val workouts_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val Workout_tv = itemView.findViewById<TextView>(R.id.tv_workout)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    interface workoutsClickListener{
        fun onItemClicked(workout: Workout)
        fun onLongItemClicked(workout: Workout, cardView: CardView)

    }



}
package com.example.compsciia.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.compsciia.Models.CompletedWorkout
import com.example.compsciia.R

class CompletedAdapter(private val context2: Context, val listener2: CompletedClickListener) :
    RecyclerView.Adapter<CompletedAdapter.CompletedViewHolder>() {

    private val completedList = ArrayList<CompletedWorkout>()
    private val fullCompletedList = ArrayList<CompletedWorkout>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        return CompletedViewHolder(
            LayoutInflater.from(context2).inflate(
                R.layout.completed_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder2: CompletedViewHolder, position: Int) {
        val currentWorkout2 = completedList[position]
        holder2.title2.text = currentWorkout2.title2
        holder2.date2.text = currentWorkout2.date2

        holder2.date2.text = currentWorkout2.date2
        holder2.date2.isSelected = true

        // Assuming you want to use a light blue color
        holder2.completed_layout.setCardBackgroundColor(0xFFADD8E6.toInt())

        holder2.completed_layout.setOnClickListener {
            listener2.onItemClicked(completedList[holder2.adapterPosition])
        }
        holder2.completed_layout.setOnLongClickListener {
            listener2.onLongItemClicked(
                completedList[holder2.adapterPosition],
                holder2.completed_layout
            )
            true
        }
    }

    override fun getItemCount(): Int {
        return completedList.size
    }

    fun updateList2(newList2: List<CompletedWorkout>) {
        fullCompletedList.clear()
        fullCompletedList.addAll(newList2)

        completedList.clear()
        completedList.addAll(fullCompletedList)
        notifyDataSetChanged()
    }

    inner class CompletedViewHolder(itemView2: View) : RecyclerView.ViewHolder(itemView2) {
        val completed_layout = itemView2.findViewById<CardView>(R.id.card_layout2)
        val title2 = itemView2.findViewById<TextView>(R.id.tv_title2)
        val date2 = itemView2.findViewById<TextView>(R.id.tv_date2)
    }

    interface CompletedClickListener {
        fun onItemClicked(completedWorkout: CompletedWorkout)
        fun onLongItemClicked(completedWorkout: CompletedWorkout, cardView: CardView)
    }
}

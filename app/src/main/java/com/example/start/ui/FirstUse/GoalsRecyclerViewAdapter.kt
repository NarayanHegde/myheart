package com.example.start.ui.FirstUse

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.start.R
import com.google.android.material.textfield.TextInputLayout

class GoalsRecyclerViewAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<GoalsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_goals_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.setText(item)
        holder.select.visibility=View.GONE
        holder.itemView.setOnClickListener {
            if(holder.select.visibility==View.GONE) {
                holder.select.visibility = View.VISIBLE
            }
            else{
                holder.select.visibility=View.GONE
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.goal_name)
        val select: LinearLayout = view.findViewById(R.id.selected_goal)


    }
}
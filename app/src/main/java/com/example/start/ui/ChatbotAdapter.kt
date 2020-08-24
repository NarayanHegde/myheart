package com.example.start.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.start.R

class ChatbotAdapter(val list:List<Pair<String,Boolean>>):RecyclerView.Adapter<ChatbotAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list[position].second){
            val layoutparams= holder.cardview.layoutParams as ViewGroup.MarginLayoutParams
            layoutparams.marginEnd=50
            holder.cardview.setCardBackgroundColor(Color.parseColor("#6200EE"))
            holder.cardview.requestLayout()
            holder.ouput.setText(list[position].first)
        }
        else{
            val layoutparams= holder.cardview.layoutParams as ViewGroup.MarginLayoutParams
            layoutparams.marginStart=50
            holder.cardview.setCardBackgroundColor(Color.parseColor("#03DAC5"))
            holder.cardview.requestLayout()
            holder.ouput.setText(list[position].first)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item,parent,false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val cardview= view.findViewById<CardView>(R.id.chat_card)
        val ouput= view.findViewById<TextView>(R.id.viewtext)
    }
}
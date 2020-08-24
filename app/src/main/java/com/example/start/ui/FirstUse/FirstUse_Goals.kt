package com.example.start.ui.FirstUse

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.start.ui.MainActivity
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.Notifications
import com.example.start.ui.MainViewModel
import com.example.start.workmanager.ScheduleNotificationWork
import kotlinx.android.synthetic.main.fragment_first_use__goals.*
import kotlinx.android.synthetic.main.fragment_goals_item.view.*


class FirstUse_Goals : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_first_use__goals, container, false)

        val list  = arrayListOf(getString(R.string.goals_physical),
            getString(R.string.goals_dieting),
            getString(R.string.goals_stress),
            getString(R.string.goals_smoking))
        val recyclerView = view.findViewById<RecyclerView>(R.id.goals_recycler)
        recyclerView.layoutManager=GridLayoutManager(context,2)
        recyclerView.adapter=GoalsRecyclerViewAdapter(list)
        // Inflate the layout for this fragment
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel=ViewModelProviders.of(this).get(MainViewModel::class.java)

        val sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.user_states),
            Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()

        skipgoals.setOnClickListener {
            mainViewModel.insertnotifications()
            Thread.sleep(1000L)
            val workRequest= OneTimeWorkRequestBuilder<ScheduleNotificationWork>()
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
            firstusecomplete()
        }

        getstarted.setOnClickListener {
            storepreferences()
            mainViewModel.insertnotifications()
            Thread.sleep(1000L)
            val workRequest= OneTimeWorkRequestBuilder<ScheduleNotificationWork>()
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
            firstusecomplete()
        }
    }

    fun storepreferences(){
        for ( i in 0..3){
            val view = goals_recycler.getChildAt(i)
            if(view.selected_goal.visibility==View.VISIBLE){
                editor.putBoolean(view.goal_name.text.toString(),true)
            }
        }
        editor.apply()
    }

    fun firstusecomplete(){
        editor.putBoolean(getString(R.string.firstuse_complete),true)
        editor.putInt("days",0)
        editor.apply()
        Intent(requireContext(), MainActivity::class.java).also{
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            it.putExtra("myheart",true)
            startActivity(it)
        }
    }



    companion object {

        @JvmStatic
        fun newInstance() = FirstUse_Goals()
    }
}
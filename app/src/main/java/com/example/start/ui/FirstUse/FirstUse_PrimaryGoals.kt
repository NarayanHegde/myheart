package com.example.start.ui.FirstUse

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.start.ui.MainActivity
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.Notifications
import com.example.start.ui.MainViewModel
import com.example.start.workmanager.ScheduleNotificationWork
import kotlinx.android.synthetic.main.fragment_first_use__primary_goals.*


class FirstUse_PrimaryGoals : Fragment() {

    private lateinit var editor:SharedPreferences.Editor
    private lateinit var mainViewModel: MainViewModel
    val CONTACT_PICKUP =1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_first_use__primary_goals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)

        editor=requireActivity().getSharedPreferences(getString(R.string.user_states),Context.MODE_PRIVATE).edit()
        val bmigoal= layoutInflater.inflate(R.layout.goals_entry_primary,goals_linear,true)
        bmigoal.findViewById<TextView>(R.id.goals_text).setText("Enter the BMI to achieve next month")

        getstarted.setOnClickListener {
            editor.putFloat("BMIGoal",bmigoal.findViewById<EditText>(R.id.user_goal).text.toString().toFloat())
            editor.apply()
            mainViewModel.insertnotifications()
            Thread.sleep(1000L)
            val workRequest= OneTimeWorkRequestBuilder<ScheduleNotificationWork>()
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
            firstusecomplete()
        }

        skip.setOnClickListener {
            mainViewModel.insertnotifications()
            Thread.sleep(1000L)
            val workRequest= OneTimeWorkRequestBuilder<ScheduleNotificationWork>()
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
            firstusecomplete()
        }

        select_contact.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.setDataAndType(ContactsContract.Contacts.CONTENT_URI,ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)
            startActivityForResult(intent,CONTACT_PICKUP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==RESULT_OK && requestCode == CONTACT_PICKUP) {
            val contactdata = data?.data
            contactdata?.let {
                val c = context?.contentResolver?.query(it, null, null, null, null)
                c?.let {
                    if(it.moveToFirst()){
                        val phoneindex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val nameindex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                        val name = it.getString(nameindex)
                        val number= it.getString(phoneindex)
                        val fixednumer=number.filter { it.isDigit() }
                        editor.putString("contactperson",fixednumer)
                        editor.apply()
                        select_contact.setText(name)
                        Toast.makeText(context,"Contact Selected: ",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
        fun newInstance() = FirstUse_PrimaryGoals()
    }
}
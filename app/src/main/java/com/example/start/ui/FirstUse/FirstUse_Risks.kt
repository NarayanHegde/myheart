package com.example.start.ui.FirstUse

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.start.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_first_use1.*
import java.util.*

@Suppress("NAME_SHADOWING")
class FirstUse_Risks : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_use1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedpreferences= requireActivity().getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE)
        val editor=sharedpreferences.edit()
        editor.putInt(getString(R.string.myheart_progress),0)
        editor.apply()
        val type=sharedpreferences.getInt("type",2)
        val risks=sharedpreferences.getString("risks","")
        var risksstring=""
        if(risks!=""){
            val type=object: TypeToken<List<String>>(){}.type
            val new= Gson().fromJson<List<String>>(risks,type)

            for (i in new.indices)
            {
                risksstring=risksstring + (i+1).toString() + ". " + new[i] + "\n"
            }
        }
        if(type==1) {
            minorrisks.setText(
                getString(
                    R.string.introduction_primordial,
                    risksstring
                )
            )
        }
        else if(type==0){
            minorrisks.setText(
                getString(R.string.introduction_primary,risksstring)
            )
        }

        letsgo.setOnClickListener {
            if (type==1) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.firstuse_fragment_container, FirstUse_Goals())
                    .commit()
            }
            else if(type==0){
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.firstuse_fragment_container,FirstUse_PrimaryGoals())
                    .commit()
            }
        }

    }



    companion object {

        @JvmStatic
        fun newInstance() = FirstUse_Risks()
    }
}
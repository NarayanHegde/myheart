package com.example.start.ui.onboarding

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_onboarding2.*
import java.lang.Exception
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class Onboarding_userdetails : Fragment() {
    private  val genders : Array<String> = arrayOf("Male","Female","Other")

    private var age: Int? = null
    private var bmi: Float? = null
    private var gender : Int? =null

    private lateinit var editor:SharedPreferences.Editor
    private lateinit var onboarding_viewmodel:OnboardingViewModel
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel= ViewModelProvider(this).get(MainViewModel::class.java)

        onboarding_viewmodel=activity?.let{ ViewModelProviders.of(it).get(OnboardingViewModel::class.java)}?:throw Exception("Invalid Activity")
        editor=requireActivity().getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE).edit()
        editor.putInt(getString(R.string.fragment_id),2)
        editor.apply()


        age=onboarding_viewmodel.getAge()
        gender=onboarding_viewmodel.getGender()
        bmi=onboarding_viewmodel.getBMI()
        if(age!=0){
            agetextanswer.setText(age.toString())
        }
        if(bmi!=0.toFloat()){
            bmitextanswer.setText(bmi.toString())
        }

        if(gender!=0){
            when(gender){
                1-> genderselect.check(R.id.buttonmale)
                2->genderselect.check(R.id.buttonfemale)
                else->genderselect.check(R.id.buttonother)
            }
        }

        genderselect.addOnButtonCheckedListener(onboarding_viewmodel.getbuttoncheckedlistener())
        agetextanswer.addTextChangedListener(onboarding_viewmodel.gettextwatcher())
        bmitextanswer.addTextChangedListener(onboarding_viewmodel.gettextwatcher())

        agetextanswer.setOnFocusChangeListener { view, b ->
            if(!b){
                val imm= requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }
        }

        onboarding_viewmodel.progress.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let{
                progressbar.setProgress(it)
            }
        })

        nextbutton.setOnClickListener {
           nextbuttonclick()
        }

        skipbutton.setOnClickListener {

            agetextanswer.setText("")
            bmitextanswer.setText("")
            genderselect.clearChecked()
            val fragment = Onboarding_medicalreport.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onboarding_fragment_container,fragment)
                .commit()
        }
    }


    override fun onPause() {
        super.onPause()

        agetextanswer.text?.let{
            try {
                editor.putInt("age",it.toString().toInt())
            }
            catch (e:Exception){}
        }
        bmitextanswer.text?.let{
            try {
                editor.putFloat("bmi",it.toString().toFloat())
            }
            catch (e:Exception){}
        }

        val gender= when(genderselect.checkedButtonId){
            R.id.buttonmale ->1
            R.id.buttonfemale -> 2
            R.id.buttonother -> 3
            else -> null
        }
        gender?.let{
//            onboarding_viewmodel.setGender(gender)
            editor.putInt("gender",it)
        }

        onboarding_viewmodel.getProgress()?.let {
            editor.putInt("progress",it)
        }
        editor.apply()
    }

    private fun nextbuttonclick(){
        var age:Int? =null
        agetextanswer.text?.let{
            try {
                age = it.toString().toInt()
            }
            catch (e:Exception){
                null
            }
        }
        var bmi:Float? = null
        bmitextanswer.text?.let {
            try {
                bmi = it.toString().toFloat()
            }
            catch (e:Exception){
                null
            }
        }

        val gender= when(genderselect.checkedButtonId){
            R.id.buttonmale ->1
            R.id.buttonfemale -> 2
            R.id.buttonother -> 3
            else -> null
        }

        bmi?.let{
            onboarding_viewmodel.setBMI(it)}
        age?.let{
            onboarding_viewmodel.setAge(it)}
        gender?.let{
            onboarding_viewmodel.setGender(it)}

        insertdatabase(age,bmi,gender)

//        TODO("Uncomment this after the insert problem sorted")
        val fragment= Onboarding_medicalreport.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.onboarding_fragment_container,fragment)
            .commit()
    }

    private fun insertdatabase(age:Int?,bmi:Float?,gender: Int?){
        age?.let {
            val inputage = DataInput(
                timestamp = Date(),
                healthvital = Converters.Indicator.General,
                datapath = Converters.Datapath.Age,
                data = it.toString(),
                source = true,
                modelname = null,
                preprocessdata = null
            )
            viewmodel.insert(inputage)
        }
        bmi?.let {
            val inputbmi=DataInput(
                timestamp = Date(),
                healthvital = Converters.Indicator.General,
                datapath = Converters.Datapath.BMI,
                data = it.toString(),
                source = true,
                modelname = null,
                preprocessdata = null
            )
            viewmodel.insert(inputbmi)
        }
        gender?.let {
            val inputgender = DataInput(
                timestamp = Date(),
                healthvital = Converters.Indicator.General,
                datapath = Converters.Datapath.Gender,
                data = genders[it - 1],
                source = true,
                modelname = null,
                preprocessdata = null
            )
            viewmodel.insert(inputgender)
        }
        Thread.sleep(1000L)
        Toast.makeText(context, "Database entry Successful", Toast.LENGTH_SHORT).show()
    }


    companion object {

        @JvmStatic
        fun newInstance() = Onboarding_userdetails()
    }
}
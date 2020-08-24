package com.example.start.ui.onboarding

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.start.R
import com.example.start.workmanager.UserTypeWork
import com.google.android.material.button.MaterialButtonToggleGroup

class OnboardingViewModel(application: Application):AndroidViewModel(application) {

    private var age:Int
    private var bmi:Float
    private var gender:Int
    private var username=MutableLiveData<String>()
    var progress:MutableLiveData<Int> =MutableLiveData(0)
    private val sharedPreferences:SharedPreferences

    init {
        val sharedpreference_name= getApplication<Application>().getString(R.string.user_states)
        sharedPreferences=  getApplication<Application>().getSharedPreferences(sharedpreference_name,Context.MODE_PRIVATE)
        val tempprogress=sharedPreferences.getInt("progress",0)
        if (tempprogress!=0){
            setProgress(tempprogress)
        }
        age = sharedPreferences.getInt("age",0)
        bmi= sharedPreferences.getFloat("bmi",0.toFloat())
        gender=sharedPreferences.getInt("gender",0)
    }

    internal fun setAge(input_age:Int){
        age= input_age
    }

    internal fun getAge() :Int?{
        return age
    }

    internal fun setUsername(input_username:String){
        username.value=input_username
    }

    internal fun getUsername():String?{
        return username.value
    }

    internal fun setGender(input_gender:Int){
        gender= input_gender
    }

    internal fun getGender() :Int?{
        return gender
    }

    internal fun setBMI(input_bmi:Float){
        bmi=input_bmi
    }

    internal fun getBMI():Float{
        return bmi
    }

    internal fun getusertype(){
        //Using the collected data , there will be a model running here that tells that the
    }

    internal fun makeworkrequest(){
        val request = OneTimeWorkRequestBuilder<UserTypeWork>()
            .build()
        WorkManager.getInstance(getApplication()).enqueue(request)
    }

    internal fun setProgress(input_progress:Int){
        progress.value=input_progress
    }

    internal fun getProgress():Int?{
        return progress.value
    }


    fun getbuttoncheckedlistener(): MaterialButtonToggleGroup.OnButtonCheckedListener{
        return MaterialButtonToggleGroup.OnButtonCheckedListener { _, _, isChecked ->
            if(isChecked){
                getProgress()?.let {
                    setProgress(it+7)
                }
            } else{
                getProgress()?.let {
                    setProgress(it-7)
                }
            }
        }
    }

    fun gettextwatcher(): TextWatcher{
        return object : TextWatcher {
            var check = false
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrEmpty() && check) {
                    check = false
                    getProgress()?.let {
                        setProgress(it - 7)
                    }
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p1 == 0 && p2 == 0) {
                    getProgress()?.let {
                        setProgress(it + 7)
                    }
                    check = true
                }
            }
        }
    }

}
package com.example.start.ui.onboarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import kotlinx.android.synthetic.main.fragment_onboarding1.*
import java.lang.Exception
import kotlin.random.Random



class Onboarding_userselfie : Fragment() {

    private var username: String? = null
    val SELFIE_CAPTURE =1
    private lateinit var onboarding_viewmodel:OnboardingViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val editor= requireActivity().getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE).edit()
        editor.putInt(getString(R.string.fragment_id),1)
        editor.apply()
        onboarding_viewmodel=activity?.let{ ViewModelProviders.of(it).get(OnboardingViewModel::class.java)}?:throw Exception("Invalid Activity")
        username= onboarding_viewmodel.getUsername()

        skip.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onboarding_fragment_container,Onboarding_userdetails())
                .commit()
        }


        username?.let{
            userwelcometext.setText(getString(R.string.userwelcome,it))
        }

        selfie.setOnClickListener{
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{intent->
                intent.resolveActivity(requireContext().packageManager)?.let{
                    startActivityForResult(intent,SELFIE_CAPTURE)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode == SELFIE_CAPTURE){
            processimage(data)
        }
    }

    private fun processimage(data: Intent?){
        // data should be used to get the bitmap of the image
        //Here is the dummy thing that fills Age, BMI with something Dummy


        val age =  Random.nextInt(20,70)
        val bmi:Float = Random.nextInt(200,300).toFloat()/10
        val gender = Random.nextInt(1,2)
        onboarding_viewmodel.setAge(age)
        onboarding_viewmodel.setGender(gender)
        onboarding_viewmodel.setBMI(bmi)
        val fragment = Onboarding_userdetails.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.onboarding_fragment_container,fragment)
            .commit()

    }

    companion object {

        @JvmStatic
        fun newInstance() = Onboarding_userselfie()
    }
}
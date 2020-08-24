package com.example.start.ui.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.google.android.gms.auth.api.signin.GoogleSignIn

class Onboarding : AppCompatActivity() {

    private var username:String? = null
    private lateinit var onboarding_viewmodel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        onboarding_viewmodel= ViewModelProviders.of(this).get(OnboardingViewModel::class.java)

        val account= GoogleSignIn.getLastSignedInAccount(this)
        account?.let{
            username = it.displayName
            username?.let{
                onboarding_viewmodel.setUsername(it)
            }
        }

        val sharedPreferences = getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE)
        val fragment_number= sharedPreferences.getInt(getString(R.string.fragment_id),0)
        val fragment = when(fragment_number){
            0-> Onboarding_consent.newInstance()
            1-> Onboarding_userselfie.newInstance()
            2-> Onboarding_userdetails.newInstance()
            3-> Onboarding_medicalreport.newInstance()
            4-> Onboarding_medicaldetails()
            else->Onboarding_socioeconomic.newInstance()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.onboarding_fragment_container,fragment)
            .commit()


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}
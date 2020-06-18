package com.example.start.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.start.MainActivity
import com.example.start.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_onboarding.*

class Onboarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val account= GoogleSignIn.getLastSignedInAccount(this)
        account?.let{
            user_display.setText(it.displayName)
        }

        proceed_button.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })
    }
}
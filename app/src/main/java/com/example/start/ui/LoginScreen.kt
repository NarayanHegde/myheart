package com.example.start.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.start.R
import com.example.start.ui.FirstUse.FirstUse
import com.example.start.ui.onboarding.Onboarding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_first_screen.*

private var onboardingcomplete = false
private var firstusecomplete=false

private lateinit var auth:FirebaseAuth
private lateinit var mGoogleSignInClient: GoogleSignInClient
private val RC_SIGN_IN =4
val basic_channel = "Basic"



class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        createNotificationChannel()
        auth= Firebase.auth
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        signin_button.setOnClickListener {
            val signinintent= mGoogleSignInClient.signInIntent
            startActivityForResult(signinintent,RC_SIGN_IN)
        }
    }


    override fun onStart() {
        super.onStart()

        val currentuser= auth.currentUser
        currentuser?.let {
            val sharedPreferences=getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE)
            onboardingcomplete=sharedPreferences.getBoolean(getString(R.string.onboarding_complete),false)
            firstusecomplete= sharedPreferences.getBoolean(getString(R.string.firstuse_complete),false)
            nextactivity()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInTask(task)
        }
    }

    private fun handleSignInTask(task: Task<GoogleSignInAccount>){
        try {
            val account = task.getResult(ApiException::class.java)
            Log.w("TAG","Proceed")
            account?.let{
                firebaseAuthWithGoogle(it.idToken)
            }

        }catch (e:ApiException){
            Log.w("errorinsignin",e.statusCode.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    nextactivity()
                } else {
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name ="Basic"
            val description = "Show basic notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(basic_channel,name,importance).apply {
                setDescription(description)
            }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun nextactivity(){
        if(onboardingcomplete && firstusecomplete){
            Intent(this, MainActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }

        }
        else if (onboardingcomplete && ! firstusecomplete){
            Intent(this,FirstUse::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
        else{
            Intent(this,Onboarding::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
    }
}
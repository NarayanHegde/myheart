package com.example.start.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.AlteredCharSequence.make
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.start.MainActivity
import com.example.start.R
import com.example.start.ui.onboarding.Onboarding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_first_screen.*


private lateinit var auth:FirebaseAuth
private lateinit var mGoogleSignInClient: GoogleSignInClient
private val RC_SIGN_IN =4

class FirstScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

//        setSupportActionBar(toolbar)

        auth= Firebase.auth

//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        account?.let {
//            startActivity(Intent(this,MainActivity::class.java))
//        }

        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        signin_button.setOnClickListener(View.OnClickListener {
            val signinintent= mGoogleSignInClient.signInIntent
            startActivityForResult(signinintent,RC_SIGN_IN)
        })
    }


    override fun onStart() {
        super.onStart()

        val currentuser= auth.currentUser
        currentuser?.let {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if(resultCode== Activity.RESULT_OK && requestCode == RC_SIGN_IN){
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
            startActivity(Intent(this,Onboarding::class.java))
        }catch (e:ApiException){
            Log.w("errorinsignin",e.statusCode.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    // ...
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }
}
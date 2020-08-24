package com.example.start.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.start.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_roadmap.*


class Assistant : Fragment() {

    private lateinit var functions:FirebaseFunctions
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roadmap, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        functions= Firebase.functions
        auth=Firebase.auth

        val list= mutableListOf<Pair<String,Boolean>>(Pair("hey",false),Pair("hey! how are you?",true))
        val adapter=ChatbotAdapter(list)
        chat_recycler.adapter=adapter

        sendbutton.setOnClickListener {
            usertext.editText?.let{
                if(!it.text.isNullOrEmpty()){
                    list.add(Pair(it.text.toString(),false))
                    adapter.notifyItemInserted(list.size-1)
                    sendmessage(it.text.toString())
                        .addOnCompleteListener {
                            if (!it.isSuccessful){
                                val e= it.exception
                                if(e is FirebaseFunctionsException){
                                    val code =e.code
                                    val details = e.details
                                    //Log.d("Firebase error",code.toString()+details.toString())
                                }
                                //Log.d("Task not completed ",e.toString())
                            }
                            else{
                                Log.d("result obtained",it.result.toString())
                            }
                        }
                }
            }
        }

    }

    private fun sendmessage(question:String): Task<String> {
        val data = hashMapOf(
            "question" to question,
            "push" to true
        )

        return functions
            .getHttpsCallable("detectIntent")
            .call(data)
            .continueWith {
                val result=it.result?.data as String
                result
            }
    }


    companion object {
        @JvmStatic
        fun newInstance() = Assistant()

    }
}
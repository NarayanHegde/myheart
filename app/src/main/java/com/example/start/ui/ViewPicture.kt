package com.example.start.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.start.MainActivity
import com.example.start.R
import com.example.start.ui.todo.Todo
import kotlinx.android.synthetic.main.activity_view_picture.*

class ViewPicture : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_picture)

        val replyintent = Intent()

//        if (getIntent().getExtras() != null ) {
//            Log.println(Log.INFO,"Savedinstancestatenull","no")
//            if (getIntent().getExtras()!!.get("URI") != null) {
//                Log.println(Log.INFO, "URIrecieved", getIntent().getExtras()!!.getString("URI")!!)
//            }
//        }
        val uri= Uri.parse(getIntent().getExtras()!!.getString("URI")!!)
        imageview.setImageURI(uri!!)

        select.setOnClickListener(View.OnClickListener { view ->
            replyintent.putExtra("URI",uri.toString())
            setResult(Activity.RESULT_OK,replyintent)
            finish()
        })

        cancel.setOnClickListener(View.OnClickListener { view ->
            setResult( Activity.RESULT_CANCELED, replyintent)
            finish()
//            val backintent= Intent(this, MainActivity::class.java)
//            startActivity(backintent)
//            onBackPressed()

        })

    }
}

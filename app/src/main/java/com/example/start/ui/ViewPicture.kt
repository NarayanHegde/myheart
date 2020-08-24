package com.example.start.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.start.R
import kotlinx.android.synthetic.main.activity_view_picture.*

class ViewPicture : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_picture)

        val replyintent = Intent()

        val uri= Uri.parse(getIntent().getExtras()!!.getString("URI")!!)
        imageview.setImageURI(uri!!)

        select.setOnClickListener(View.OnClickListener { _ ->
            replyintent.putExtra("URI",uri.toString())
            setResult(Activity.RESULT_OK,replyintent)
            finish()
        })

        cancel.setOnClickListener(View.OnClickListener { _ ->
            setResult( Activity.RESULT_CANCELED, replyintent)
            finish()
        })

    }
}

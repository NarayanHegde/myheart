package com.example.start

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.start.animation.ZoomOutPageTransformer
import com.example.start.database.DataInput
import com.example.start.ui.ViewPicture
import com.example.start.ui.main.SectionsPagerAdapter
import com.example.start.ui.todo.TodoViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoViewmodel:TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoViewmodel=ViewModelProvider(this).get(TodoViewModel::class.java)

        ////////////////////////Setup TABS Transition///////////////////////
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
//        viewPager.setPageTransformer(true,ZoomOutPageTransformer())
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        //////////////////////////////////////////////////////////////////

        val fab :ImageButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val settingsintent=Intent(this,com.example.start.settings.Settings::class.java)
            startActivity(settingsintent)
//            todoViewmodel.delete()
//            Snackbar.make(view, "All images deleted", Snackbar.LENGTH_LONG)
//                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }


}
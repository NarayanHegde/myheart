package com.example.start.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.start.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewmodel=ViewModelProvider(this).get(MainViewModel::class.java)

        ////////////////////////Setup TABS Transition///////////////////////
        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        if(intent.getBooleanExtra("myheart",false)){
            viewPager.setCurrentItem(1)
        }
        //////////////////////////////////////////////////////////////////

        val fab :ImageButton = findViewById(R.id.fab)
        fab.setOnClickListener { _ ->
            val settingsintent=Intent(this, com.example.start.ui.settings.Settings::class.java)
            startActivity(settingsintent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }


}
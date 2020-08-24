package com.example.start.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.start.R
import com.example.start.ui.LoginScreen
import com.example.start.ui.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private var check=true
        private lateinit var viewmodel: MainViewModel

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            viewmodel=ViewModelProvider(this).get(MainViewModel::class.java)

        }


        override fun onPreferenceTreeClick(preference: Preference?): Boolean {

            preference?.let{
                if(it.key.equals("delete")){
                    deletedata()
                    return check
                }
                else if(it.key.equals("logout")){
                    logout()
                    return false
                }
            }
            return super.onPreferenceTreeClick(preference)
        }

        private fun deletedata(){
            MaterialAlertDialogBuilder(context)
                .setTitle(resources.getString(R.string.delete_title))
                .setMessage(resources.getString(R.string.delete_supporting_text))
                .setNeutralButton(resources.getString(R.string.cancel)) { _, _ ->
                    check=false
                }
                .setPositiveButton(resources.getString(android.R.string.ok)) { _, _ ->
                    viewmodel.delete()
                    Toast.makeText(requireContext(),"All the data has been deleted",Toast.LENGTH_SHORT).show()
                    check=true

                }
                .show()
        }

        private fun logout(){
            Firebase.auth.signOut()
            val intent = Intent(requireContext(),LoginScreen::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
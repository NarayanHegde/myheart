package com.example.start.settings

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.start.R
import com.example.start.ui.FirstScreen
import com.example.start.ui.todo.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
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
        private lateinit var viewmodel:TodoViewModel

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            viewmodel=ViewModelProvider(this).get(TodoViewModel::class.java)
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
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                    // Respond to neutral button press
                    check=false
                }
                .setPositiveButton(resources.getString(android.R.string.ok)) { dialog, which ->
                    viewmodel.delete()
                    Toast.makeText(requireContext(),"All the data has been deleted",Toast.LENGTH_SHORT).show()
                    check=true
                    // Respond to positive button press
                }
                .show()
        }

        private fun logout(){
            Firebase.auth.signOut()
            val intent = Intent(requireContext(),FirstScreen::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
package com.example.start.ui.onboarding

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.start.R
import kotlinx.android.synthetic.main.fragment_onboarding3.*


class Onboarding_medicalreport : Fragment() {

    private val REQUEST_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding3, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences= requireContext().getSharedPreferences(getString(R.string.user_states),
            Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.putInt(getString(R.string.fragment_id),3)
        editor.apply()

        report.setOnClickListener {
            takeimage()
        }

        skipbutton.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onboarding_fragment_container,Onboarding_medicaldetails())
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== RESULT_OK && requestCode == REQUEST_PHOTO){
            data?.let{
                val bitmap= data.extras!!.get("data") as Bitmap
                val fragment = Onboarding_medicaldetails.newInstance(2,bitmap)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.onboarding_fragment_container,fragment)
                    .commit()
            }

        }
    }

    private fun takeimage(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{intent ->
            intent.resolveActivity(requireActivity().packageManager)?.let{
                startActivityForResult(intent,REQUEST_PHOTO)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = Onboarding_medicalreport()
    }
}
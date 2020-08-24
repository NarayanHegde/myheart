package com.example.start.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.start.R
import kotlinx.android.synthetic.main.fragment_onboarding_consent.*


class Onboarding_consent : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_consent, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        agree.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.onboarding_fragment_container,Onboarding_userselfie.newInstance())
                .commit()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = Onboarding_consent()
    }
}
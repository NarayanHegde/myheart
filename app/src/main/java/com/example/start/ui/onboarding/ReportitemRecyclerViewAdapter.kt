package com.example.start.ui.onboarding

import android.app.Activity
import android.text.InputType
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ReportitemRecyclerViewAdapter(
    private val values: List<Pair<String,String>>, context: FragmentActivity
) : RecyclerView.Adapter<ReportitemRecyclerViewAdapter.ViewHolder>() {

    val onboardingViewModel= ViewModelProviders.of(context).get(OnboardingViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_onboarding4, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.editText?.addTextChangedListener(onboardingViewModel.gettextwatcher())
        holder.idView.hint = item.first
        holder.idView.editText?.setText(item.second)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextInputLayout = view.findViewById(R.id.item_number)


    }
}
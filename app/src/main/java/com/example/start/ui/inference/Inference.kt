package com.example.start.ui.inference

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.start.R
import com.example.start.ui.todo.TodoViewModel
import kotlinx.android.synthetic.main.activity_view_picture.*
import kotlinx.android.synthetic.main.fragment_inference.*
import java.util.*


class Inference : Fragment() {


    private lateinit var viewmodel: TodoViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inference, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel=ViewModelProvider(this).get(TodoViewModel::class.java)
        val random_number = random_show
        val random=Random()
        viewmodel.allsources.observe(viewLifecycleOwner, Observer {
            if (it.size != 0) {
//                Log.println(Log.INFO,"URI",it[0].data)
                appCompatImageView.setImageURI(Uri.parse(it[0].data))
                random_number.setText(random.nextInt(30 - 5).toString())
            }
        })
    }

    companion object {

        fun newInstance() = Inference()

    }
}
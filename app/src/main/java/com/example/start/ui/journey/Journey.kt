package com.example.start.ui.journey

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.start.R

import com.example.start.ui.journey.dummy.DummyContent
import com.example.start.ui.journey.dummy.DummyContent.DummyItem
import com.example.start.ui.todo.TodoViewModel


class Journey : Fragment() {

    // TODO: Customize parameters
    private lateinit var viewModel: TodoViewModel
    private var columnCount = 2
    private lateinit var myadapter : MyImageRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_journey_list, container, false)

        viewModel=ViewModelProvider(this).get(TodoViewModel::class.java)



        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                myadapter=MyImageRecyclerViewAdapter()
                adapter = myadapter
            }
        }
        //////////////////////////////////////////

        viewModel.allsources.observe(viewLifecycleOwner, Observer {
            myadapter.setvalues(it)
        })

        return view

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            Journey().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

package com.example.start.ui.journey

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.start.R
import com.example.start.database.FileData


import kotlinx.android.synthetic.main.fragment_journey.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyImageRecyclerViewAdapter(
) : RecyclerView.Adapter<MyImageRecyclerViewAdapter.ViewHolder>() {

//    private val mOnClickListener: View.OnClickListener

    private var mValues = emptyList<FileData>()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageview :ImageView = mView.grid_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_journey, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.imageview.setImageURI(Uri.parse(item.data))


//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
    }

    override fun getItemCount(): Int = mValues.size

    internal fun setvalues( values : List<FileData>) {
         mValues=values
        notifyDataSetChanged()
    }


}

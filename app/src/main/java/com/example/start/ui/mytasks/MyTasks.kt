package com.example.start.ui.mytasks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.ui.MainViewModel
import com.example.start.ui.ViewPicture
import kotlinx.android.synthetic.main.todo_fragment.*
import java.util.*

class MyTasks : Fragment() {


    lateinit var currentPhotoPath:String
    private lateinit var viewModel: MainViewModel
    lateinit var photoURI:Uri

    companion object {
        fun newInstance() = MyTasks()
    }
    private val PICK_IMAGE =1
    private val newviewpicture=2
    private val REQUEST_TAKE_PHOTO=3


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.todo_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.alltasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let{
                tasks_recycler.adapter=TasksAdapter(it,requireActivity(),this)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            when (requestCode){
                PICK_IMAGE -> handlegalleryresult(data)
                newviewpicture -> confirmselectionresult(data)
                REQUEST_TAKE_PHOTO -> handlecameraresult()
            }
        }

        if(resultCode== Activity.RESULT_CANCELED && requestCode == newviewpicture){
            Toast.makeText(context, "No Image is Selected", Toast.LENGTH_LONG).show()
        }

    }

    private fun handlegalleryresult(data: Intent?){
        if (data!=null) {
            val fullPhotoUri: Uri? = data.data
            requireContext().contentResolver.takePersistableUriPermission(fullPhotoUri!!, Intent.FLAG_GRANT_READ_URI_PERMISSION )
            val selectintent = Intent(context, ViewPicture::class.java)
            val bundle= Bundle()
            bundle.putString("URI",fullPhotoUri.toString())
//                Log.println(Log.INFO,"ACTIVITYINTENT","arrived")
            selectintent.putExtras(bundle)
//                Log.println(Log.INFO,"URIsend",bundle.getString("URI")!!)
            startActivityForResult(selectintent,newviewpicture)
        }
    }

    private fun confirmselectionresult(data: Intent?){
//        todoViewmodel.setPicture()
        data?.getStringExtra("URI")?.let{
            val input = DataInput(
                timestamp = Date(),
                healthvital = Converters.Indicator.BMI,
                datapath = Converters.Datapath.Image,
                data = it,
                source = true,
                modelname = "Face2BMI",
                preprocessdata = null
            )
            viewModel.insert(input)
        }
        completetask()
        Toast.makeText(context, "Selected an Image", Toast.LENGTH_LONG).show()
    }

    private fun handlecameraresult(){
        completetask()
        Toast.makeText(context, "Selected an Image", Toast.LENGTH_LONG).show()
    }

    private fun completetask(){
        viewModel.updatephototask()
    }

}

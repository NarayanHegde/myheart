package com.example.start.ui.todo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.ui.ViewPicture
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.todo_fragment.*
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Todo : Fragment() {

    val imagesdialog = arrayOf("Select From Gallery","Take Photo")

    lateinit var currentPhotoPath:String
    private lateinit var viewModel: TodoViewModel
    lateinit var photoURI:Uri

    companion object {
        fun newInstance() = Todo()
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
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

//        cardview.setCardBackgroundColor(parseColor("#6FFF75"))
//        upload_picture_text.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_check_circle_black_24dp),null,null,null)
        ///////////////Changing the CardView background color//////////////////////
//        viewModel.getPicture().observe(viewLifecycleOwner, Observer {picture ->
//            Log.println(Log.INFO,"valuepictureinTODO",picture.toString())
//            cardview.setCardBackgroundColor(parseColor("#6FFF75"))
//            upload_picture_text.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.ic_check_circle_black_24dp),null,null,null)
//
//        })
        /////////////////////////////////////////////////////////////

        /////////////////////Card Click////////////////////////////////////////
        val clicklistener: View.OnClickListener= View.OnClickListener { view ->

            MaterialAlertDialogBuilder(context)
                .setItems(imagesdialog) { dialog, which ->
//                    Log.println(Log.INFO,"which",which.toString())
                    when(which) {
                        0 -> dispatchGalleryIntent()
                        1 -> dispatchTakePictureIntent()
                    }
                }
                .show()
        }
        cardview.setOnClickListener(clicklistener)
        /////////////////////////////////////////////////////////////////////
    }

    private fun dispatchGalleryIntent(){
        Intent(Intent.ACTION_OPEN_DOCUMENT).also{galleryintent ->
            galleryintent.setType("image/*")
            galleryintent.resolveActivity(requireContext().packageManager)?.also{
                val mimety= arrayOf<String>("image/jpeg","image/png")
                galleryintent.putExtra(Intent.EXTRA_MIME_TYPES,mimety)
                startActivityForResult(galleryintent,PICK_IMAGE)
//                getActivity()!!.startActivityForResult(galleryintent,PICK_IMAGE)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.println(Log.INFO,"yo","noyo")
//                    ...
                    null
//
                }
                Log.println(Log.INFO,"yo","yesyo")
                // Continue only if the File was successfully created
                photoFile?.also {
                        photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.start.fileprovider",
                        it
                    )
                    val input = DataInput(Date(),Converters.Indicator.BMI,Converters.Datapath.Image,photoURI.toString(),true,"Face2BMI",null)
                    viewModel.insert(input)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
//                    getActivity()!!.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
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
            val input = DataInput(Date(),Converters.Indicator.BMI,Converters.Datapath.Image,it,true,"Face2BMI",null)
            viewModel.insert(input)
        }
        Toast.makeText(context, "Selected an Image", Toast.LENGTH_LONG).show()
    }

    private fun handlecameraresult(){
        Toast.makeText(context, "Selected an Image", Toast.LENGTH_LONG).show()
    }

}

package com.example.start.ui.mytasks

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.database.Tasks
import com.example.start.ui.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(val list:List<Tasks>, val context: Activity, val fragcontext: Fragment) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    val imagesdialog = arrayOf("Select From Gallery","Take Photo")
    private val PICK_IMAGE = 1
    private val REQUEST_TAKE_PHOTO=3
    lateinit var photoURI: Uri
    lateinit var currentPhotoPath:String
    private  val viewModel: MainViewModel = ViewModelProviders.of(fragcontext).get(
        MainViewModel::class.java)
    val takephoto: View.OnClickListener= View.OnClickListener { _ ->

        MaterialAlertDialogBuilder(fragcontext.context)
            .setItems(imagesdialog) { _, which ->
                when(which) {
                    0 -> dispatchGalleryIntent()
                    1 -> dispatchTakePictureIntent()
                }
            }
            .show()
    }
    val taskcomplete : View.OnClickListener = View.OnClickListener {

        val task = it?.findViewById<TextView>(R.id.tasks_text)?.text
        MaterialAlertDialogBuilder(fragcontext.requireContext())
            .setTitle(task)
            .setPositiveButton("Mark as complete"){_, _ ->
                task?.let{
                    viewModel.updatetask(it.toString())
                }
            }
            .setNeutralButton("Cancel"){ _, _ ->
            }
            .show()

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task=list[position]
        holder.textview.setText(task.task)
        if (task.task=="Upload a Selfie"){
            holder.card.setOnClickListener(takephoto)
        }
        else{
            holder.card.setOnClickListener(taskcomplete)
        }
        if(task.completed){
            holder.card.setCardBackgroundColor(Color.parseColor("#83E487"))
            holder.textview.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_circle_24,0,0,0)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.taska_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount():Int=list.size

    private fun dispatchGalleryIntent(){
        Intent(Intent.ACTION_OPEN_DOCUMENT).also{ galleryintent ->
            galleryintent.setType("image/*")
            galleryintent.resolveActivity(context.packageManager)?.also{
                val mimety= arrayOf<String>("image/jpeg","image/png")
                galleryintent.putExtra(Intent.EXTRA_MIME_TYPES,mimety)
                fragcontext.startActivityForResult(galleryintent,PICK_IMAGE)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        context,
                        "com.example.start.fileprovider",
                        it
                    )
                    val input = DataInput(
                        timestamp = Date(),
                        healthvital = Converters.Indicator.BMI,
                        datapath = Converters.Datapath.Image,
                        data = photoURI.toString(),
                        source = true,
                        modelname = "Face2BMI",
                        preprocessdata = null
                    )
                    viewModel.insert(input)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                    fragcontext.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card =view.findViewById<CardView>(R.id.tasks_card)
        val textview = view.findViewById<TextView>(R.id.tasks_text)
    }

}
package com.example.start.ui.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.ui.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_onboarding4_list.*
import kotlinx.android.synthetic.main.fragment_onboarding4_list.view.*
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class Onboarding_medicaldetails : Fragment() {

    private var columnCount = 1
    private var imagebitmap : Bitmap? =null
    private lateinit var editor: SharedPreferences.Editor
    val hashmap :MutableMap<String,String> =  HashMap()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var onboarding_viewmodel: OnboardingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            val bytearray = it.getByteArray(ARG_BITMAP)
            bytearray?.let{
                imagebitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding4_list, container, false)
        val newview = view.list as RecyclerView
        newview.layoutManager=GridLayoutManager(context,2)
        getvitalsfromreport()
        newview.adapter = ReportitemRecyclerViewAdapter(hashmap.toList(),requireActivity())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)
        onboarding_viewmodel=ViewModelProviders.of(requireActivity()).get(OnboardingViewModel::class.java)

        val sharedPreferences=requireActivity()
            .getSharedPreferences(getString(R.string.user_states), Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        editor.putInt(getString(R.string.fragment_id),4)
        editor.apply()



        for(i in hashmap.toList().indices) {
            sharedPreferences.getInt(hashmap.toList()[i].first, 0).let {
                try {
                    hashmap[hashmap.toList()[i].first]=it.toString()
                }
                catch (e:Exception){}
            }
        }
        list.adapter!!.notifyDataSetChanged()

        onboarding_viewmodel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                progressbar.setProgress(it)
            }
        })

        skipbutton.setOnClickListener {
            clearalltext()
            nextfragment()
        }

        nextbutton.setOnClickListener {
            enterintodb()
            Thread.sleep(1000L)
            nextfragment()
        }

    }

    private fun clearalltext(){
        for (i in  hashmap.toList().indices) {
            val view = list.getChildAt(i)
            val inputtext= view.findViewById<TextInputLayout>(R.id.item_number)
            inputtext.editText?.setText("")
        }
    }

    private fun getvitalsfromreport() {
        // Running an OCR model to get vitals from the report , the report is stored as imagebitmap
        // Using dummy for now
        hashmap["Diastolic BP"] = ""
        hashmap["Systolic BP"] = "120"
        hashmap["HDL(in mg/L)"] = "60"
        hashmap["LDL(in mg/L)"]= "70"
        hashmap["Total Cholesterol(in mg/L)"]="170"
        hashmap["HbA1c(in %)"]="5"

    }


    override fun onPause() {
        super.onPause()

        for (i in  hashmap.toList().indices) {
            val view = list.getChildAt(i)
            val inputtext= view.findViewById<TextInputLayout>(R.id.item_number)
            inputtext.editText?.let{
                try {
                    editor.putInt(inputtext.hint.toString(),it.text.toString().toInt())
                }
                catch (e:Exception){}
            }
        }
        onboarding_viewmodel.getProgress()?.let {
            editor.putInt("progress",it)
        }

        editor.apply()
    }

    private fun insertintodb(datapath: Converters.Datapath,data:String){
        val dataInput = DataInput(
            timestamp = Date(),
            healthvital = Converters.Indicator.General,
            datapath = datapath,
            data = data,
            source = true,
            modelname = null,
            preprocessdata = null
        )
        mainViewModel.insert(dataInput)
    }

    private fun nextfragment(){
        val fragment = Onboarding_socioeconomic.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.onboarding_fragment_container,fragment)
            .commit()
    }

    private fun enterintodb(){
        for (i in  hashmap.toList().indices) {
            val view = list.getChildAt(i)
            val inputtext= view.findViewById<TextInputLayout>(R.id.item_number)
            inputtext.editText?.let{
                try {
                    when(inputtext.hint.toString()[0]){
                        'D'-> insertintodb(Converters.Datapath.BPLOW,it.text.toString())
                        'S'-> insertintodb(Converters.Datapath.BPHIGH,it.text.toString())
                        'H'->if(inputtext.hint.toString()[2]=='D') {
                            insertintodb(Converters.Datapath.HDL, it.text.toString())
                        }
                        else{
                            insertintodb(Converters.Datapath.HbA1c,it.text.toString())
                        }
                        'L'->insertintodb(Converters.Datapath.LDL,it.text.toString())
                        'T'->insertintodb(Converters.Datapath.TotalCholesterol,it.text.toString())
                        else->null
                    }
                }
                catch (e:Exception){}
            }
        }
    }

    companion object {


        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_BITMAP = " image-bitmap"

        @JvmStatic
        fun newInstance(columnCount: Int, imageBitmap: Bitmap) =
            Onboarding_medicaldetails().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                    val stream =ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.PNG,100 , stream)
                    putByteArray(ARG_BITMAP, stream.toByteArray())
                }
            }
    }
}
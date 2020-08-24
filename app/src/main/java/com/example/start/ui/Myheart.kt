package com.example.start.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.fragment_myheart.*


class Myheart : Fragment() {


    private lateinit var mainViewModel: MainViewModel
    private var backindex =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_myheart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel=ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel.bmi.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                vital1_val.setText(it[0].data)
            }
            else{
                vital1_val.setText("-")
            }
        })

        mainViewModel.sleep.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                vital3_val.setText(it[0].data)
            }
            else{
                vital3_val.setText("-")
            }
        })

        var bplow=""
        mainViewModel.bplow.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                bplow=it[0].data
            }
        })

        mainViewModel.bphigh.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                if(bplow==""){
                    vital2_val.setText("-")
                }
                else{
                    vital2_val.setText(bplow + "/" + it[0].data)
                }
            }
            else{
                vital2_val.setText("-")
            }
        })

        val seriesitem = SeriesItem.Builder(Color.parseColor("#FFFFFF"))
            .setRange(0F, 100F,0F)
            .build()

        backindex= dynamicarcview.addSeries(seriesitem)


//        whatsapp_button.setOnClickListener {
//            val uri= Uri.parse("android.resource://com.example.start/drawable/achievement")
//            try {
//                requireActivity().packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA)
//                val intent=Intent()
//                intent.setAction(Intent.ACTION_SEND)
//                intent.putExtra(Intent.EXTRA_STREAM,uri)
//                intent.setType("image/jpeg")
//                intent.setPackage("com.whatsapp")
//                startActivity(intent)
//
//            }
//            catch (e:PackageManager.NameNotFoundException){
//                Toast.makeText(context,"Whatsapp not installed", Toast.LENGTH_SHORT).show()
//            }
//
//        }

    }


    override fun onResume() {
        super.onResume()

        val sharedPreferences=requireActivity().getSharedPreferences(getString(R.string.user_states),Context.MODE_PRIVATE)
        val progress= sharedPreferences.getInt(getString(R.string.myheart_progress),0)
        dynamicarcview.addEvent(DecoEvent.Builder(progress.toFloat()).setIndex(backindex).build())
        goal_score.setText(getString(R.string.points_myheart,progress,100))
    }

    companion object {

        @JvmStatic
        fun newInstance() = Myheart()
    }
}
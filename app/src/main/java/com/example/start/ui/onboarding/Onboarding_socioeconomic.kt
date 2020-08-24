package com.example.start.ui.onboarding

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.ui.MainViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_onboarding5.*
import java.lang.Exception
import java.util.*


class Onboarding_socioeconomic : Fragment() {

    private lateinit var onboarding_viewmodel:OnboardingViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding5, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)
        onboarding_viewmodel=ViewModelProviders.of(requireActivity()).get(OnboardingViewModel::class.java)
        sharedPreferences= requireContext().getSharedPreferences(getString(R.string.user_states),
            Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        editor.putInt(getString(R.string.fragment_id),5)
        editor.apply()
        restorestate()

        onboarding_viewmodel.progress.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                progressbar.progress = it
            }
        })

        finishbutton.setOnClickListener {
            enterintodb()
            onboarding_viewmodel.makeworkrequest()
            editor.putBoolean(getString(R.string.onboarding_complete),true)
            editor.putInt(getString(R.string.fragment_id),1)
            editor.apply()

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.onboarding_complete_title))
                .setMessage(resources.getString(R.string.onboarding_complete_description))
                .setPositiveButton(getString(android.R.string.ok)){_,_ ->
                    requireActivity().finishAndRemoveTask()
                }
                .show()
        }
    }

    override fun onPause() {
        super.onPause()

        textsaveonpause(supporttextanswer,"supportpersons")
        textsaveonpause(sleeptextanswer,"sleephours")
        textsaveonpause(stresstextanswer,"stressfulldays")
        togglesaveonpause(medicationanswer,R.id.yesmedication,R.id.nomedication,"medication")
        togglesaveonpause(smokeanswer,R.id.yessmoke,R.id.nosmoke,"smoke")
        togglesaveonpause(historyanswer,R.id.yeshistory,R.id.nohistory,"history")
        onboarding_viewmodel.getProgress()?.let {
            editor.putInt("progress",it)
        }
        editor.apply()
    }

    private fun restorestate(){
        val supportpersons= sharedPreferences.getInt("supportpersons",-1)
        val sleepinghours = sharedPreferences.getInt("sleephours",-1)
        val stressfuldays = sharedPreferences.getInt("stressfulldays",-1)
        val smoking = sharedPreferences.getInt("smoke",-1)
        val medication = sharedPreferences.getInt("medication",-1)
        val history = sharedPreferences.getInt("history",-1)

        setinputtext(supportpersons,supporttext)
        setinputtext(sleepinghours,sleeptext)
        setinputtext(stressfuldays,stresstext)
        setinputbutton(smoking,smokeanswer,R.id.yessmoke,R.id.nosmoke)
        setinputbutton(medication,medicationanswer,R.id.yesmedication,R.id.nomedication)
        setinputbutton(history,historyanswer,R.id.yeshistory,R.id.nohistory)
    }

    private fun setinputtext(value:Int,textInputLayout: TextInputLayout){
        if (value!=-1){
            textInputLayout.editText?.setText(value.toString())
        }
    }

    private fun setinputbutton(value:Int, toggle:MaterialButtonToggleGroup, yesbutton:Int,nobutton:Int){
        if(value!=-1){
            when(value){
                0-> toggle.check(nobutton)
                1-> toggle.check(yesbutton)
            }
        }
    }

    private fun insert(datapath: Converters.Datapath, data:String){
        val dataInput =DataInput(
            timestamp = Date(),
            healthvital = Converters.Indicator.SocioEconomic,
            datapath = datapath,
            data = data,
            source = true,
            modelname = null,
            preprocessdata = null
        )

        mainViewModel.insert(dataInput)
    }

    private fun textanswer(textInputLayout: TextInputLayout,datapath: Converters.Datapath){
        textInputLayout.editText?.let{
            try{
                if (!it.text.isNullOrEmpty()){
                    insert(datapath,it.text.toString())
                }
            }
            catch (e:Exception){}
        }
    }

    private fun toggleanswer(toggle: MaterialButtonToggleGroup,yesbutton: Int,nobutton: Int,datapath: Converters.Datapath){
        when(toggle.checkedButtonId){
            yesbutton-> insert(datapath,"yes")
            nobutton -> insert(datapath,"no")
        }
    }

    private fun enterintodb(){
        textanswer(supporttext, Converters.Datapath.Support)
        textanswer(sleeptext,Converters.Datapath.Sleep)
        textanswer(stresstext,Converters.Datapath.Stress)
        toggleanswer(smokeanswer,R.id.yessmoke,R.id.nosmoke,Converters.Datapath.Smoke)
        toggleanswer(historyanswer,R.id.yeshistory,R.id.nohistory,Converters.Datapath.History)
        toggleanswer(medicationanswer,R.id.yesmedication,R.id.nomedication,Converters.Datapath.Medication)

    }

    private fun textsaveonpause(textInputEditText: TextInputEditText,key: String){

            try {
                editor.putInt(key, textInputEditText.text.toString().toInt())
                editor.apply()
            }
            catch (e:Exception){}

    }

    private fun togglesaveonpause(toggle: MaterialButtonToggleGroup,yesbutton: Int,nobutton: Int,key: String){
        when(toggle.checkedButtonId){
            yesbutton-> editor.putInt(key,1)
            nobutton -> editor.putInt(key,0)
            else -> Log.d("anytogglebuton",toggle.checkedButtonId.toString() + " " + yesbutton.toString())
        }
        editor.apply()
    }



    companion object {

        @JvmStatic
        fun newInstance() = Onboarding_socioeconomic()
    }
}
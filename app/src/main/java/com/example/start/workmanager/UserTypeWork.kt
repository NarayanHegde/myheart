package com.example.start.workmanager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.contentValuesOf
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.InputDatabase
import com.example.start.database.Tasks
import com.example.start.ui.LoginScreen
import com.google.gson.Gson
import org.json.JSONObject
import java.util.*
import kotlin.random.Random
import kotlin.reflect.typeOf

class UserTypeWork(val appContext: Context, workerparams: WorkerParameters ): CoroutineWorker(appContext,workerparams) {

    override suspend fun doWork(): Result {
//        Similarly , you can get all the input from the inputData and then run a function that tells the user type
//        val age= inputData.getString("age")

//        TODO("Implement the function below")
//        getusertype(age,bmi,gender,socioeconomic_factors)

        val basic_channel="Basic"
        val intent = Intent(applicationContext,LoginScreen::class.java)
        val pendingIntent= PendingIntent.getActivity(applicationContext,0,intent,0)
        val builder= NotificationCompat.Builder(applicationContext,basic_channel)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("We are Ready")
            .setContentText("We are ready for you please visit ")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)){
            notify(0,builder.build())
        }

        val edit= appContext.getSharedPreferences(appContext.getString(R.string.user_states),Context.MODE_PRIVATE).edit()
//        val randomint= Random.nextInt(0,2)
        val randomint=0
        edit.putInt("type",randomint)
        if(randomint==1){
            val mildrisks=appContext.resources.getStringArray(R.array.mildrisks).toMutableList()
            val rand_int= Random.nextInt(1,mildrisks.size+1)
            Collections.shuffle(mildrisks)
            val risks = mildrisks.take(rand_int)
            val gson= Gson().toJson(risks)
            edit.putString("risks",gson)
        }
        else{
            val majorrrisks=appContext.resources.getStringArray(R.array.majorrisks).toMutableList()
            val rand_int= Random.nextInt(1,majorrrisks.size+1)
            Collections.shuffle(majorrrisks)
            val risks = majorrrisks.take(rand_int)
            val gson= Gson().toJson(risks)
            edit.putString("risks",gson)
        }
        edit.apply()
        return Result.success()
    }
}
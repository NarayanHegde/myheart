package com.example.start.workmanager

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.InputDatabase
import com.example.start.database.Tasks
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleNotificationWork(val context: Context,workerParameters: WorkerParameters):CoroutineWorker(context,workerParameters) {

    override suspend fun doWork(): Result {
        val sharedPreferences=context.getSharedPreferences(context.getString(R.string.user_states),Context.MODE_PRIVATE)
        val days=sharedPreferences.getInt("days",0)
        val list=InputDatabase.getDatabase(context).notificationsDao().selectalllist()
        list.let {
            for (i in it.indices) {
                val notification=list[i]
                val inputdata = Data.Builder()
                    .putString("title",notification.title)
                    .putString("content",notification.notification)
                    .putString("type",notification.type.toString())
                    .putString("entrytype",notification.entryType.toString())
                    .putString("frequency",notification.frequency.toString())
                    .build()

                val c= Calendar.getInstance()
                c.add(Calendar.DAY_OF_MONTH,0)
                c.set(Calendar.HOUR_OF_DAY,0)
                c.set(Calendar.MINUTE,0)
                c.set(Calendar.SECOND,0)
                val time =System.currentTimeMillis() - c.timeInMillis
                if(time<list[i].time && ( notification.frequency ==Converters.Frequency.Daily || (notification.frequency==Converters.Frequency.Weekly&& days==0 ))) {
                    Log.d("scheduledonerequest",list[i].notification)
                    val workRequest = OneTimeWorkRequestBuilder<NotificationWork>()
                        .setInputData(inputdata)
                        .setInitialDelay(list[i].time-time,TimeUnit.MILLISECONDS)
                        .build()

                    WorkManager.getInstance(context).enqueue(workRequest)
                }

//                InputDatabase.getDatabase(context).tasksDao().inserttask(Tasks(task = notification.notification , type = notification.type,entrytype = notification.entryType,frequency = notification.frequency,completed = false,target=null))
            }
        }
        val editor = sharedPreferences.edit()
        editor.putInt("days",(days+1)%7)
        editor.apply()
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH,1)
        c.set(Calendar.HOUR_OF_DAY,0)
        c.set(Calendar.MINUTE,0)
        c.set(Calendar.SECOND,0)

        val time= c.timeInMillis-System.currentTimeMillis()

        val notificationrequest= OneTimeWorkRequestBuilder<ScheduleNotificationWork>()
            .setInitialDelay(time,TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(notificationrequest)


        return Result.success()
    }

}
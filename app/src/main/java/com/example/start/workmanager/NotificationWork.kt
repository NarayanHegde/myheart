package com.example.start.workmanager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.start.R
import com.example.start.database.Converters
import com.example.start.database.DataInput
import com.example.start.database.InputDatabase
import com.example.start.database.Tasks
import java.util.concurrent.TimeUnit

class NotificationWork(val context: Context,workerParameters: WorkerParameters):
    CoroutineWorker(context,workerParameters) {

    val basic_channel="Basic"

    override suspend fun doWork(): Result {

        val title = inputData.getString("title")
        val content= inputData.getString("content")
        val frequency=Converters.Frequency.valueOf(inputData.getString("frequency")!!)
        val type=Converters.TasksType.valueOf(inputData.getString("type")!!)
        val entryType=Converters.EntryType.valueOf(inputData.getString("entrytype")!!)
        val builder= NotificationCompat.Builder(context,basic_channel)
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)){
            notify(0,builder.build())
        }

        InputDatabase.getDatabase(context).tasksDao().inserttask(Tasks(task = content!!,type = type,entrytype = entryType,frequency = frequency,completed = false,target = null))

        val data = Data.Builder()
            .putString("task",content)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<TaskRefresh>()
            .setInputData(data)
            .setInitialDelay(3L,TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)

        return Result.success()
    }
}
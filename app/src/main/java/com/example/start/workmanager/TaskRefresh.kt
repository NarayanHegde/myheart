package com.example.start.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.start.R
import com.example.start.database.InputDatabase

class TaskRefresh(val context: Context,workerParameters: WorkerParameters):CoroutineWorker(context,workerParameters) {

    override suspend fun doWork(): Result {

        val inputdatabase = InputDatabase.getDatabase(context)
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_states),Context.MODE_PRIVATE)
        val task = inputData.getString("task")
        task?.let{
             val list=inputdatabase.tasksDao().gettaskwithname(it)
            if(!list.isNullOrEmpty()){
                if(list[0].completed){
                    val progress = sharedPreferences.getInt(context.getString(R.string.myheart_progress),0)+5
                    val editor=sharedPreferences.edit()
                    editor.putInt(context.getString(R.string.myheart_progress),progress)
                    editor.apply()
                }
                inputdatabase.tasksDao().deletetaskwithname(it)
            }
        }
        return Result.success()
    }
}
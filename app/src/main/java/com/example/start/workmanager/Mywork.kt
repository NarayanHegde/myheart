package com.example.start.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class Mywork(appContext: Context, workerparams: WorkerParameters ): Worker(appContext,workerparams) {

    override fun doWork(): Result {
//        TODO("Not yet implemented")

        return Result.success()
    }
}
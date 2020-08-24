package com.example.start.database

import androidx.lifecycle.LiveData
import java.io.DataInput
import java.io.File

class Repository(private val inputDao: InputDao, private val tasksDao: TasksDao, private val notificationsDao: NotificationsDao, private val dashboardDao: DashboardDao) {

    val imageallall:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.Image)
    val alldata:LiveData<List<com.example.start.database.DataInput>> = inputDao.getAllEntries()
    val alltasks:LiveData<List<Tasks>> =tasksDao.getalltasks()
    val allnotifications: LiveData<List<Notifications>> = notificationsDao.selectall()
    val bmi:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.BMI)
    val bplow:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.BPLOW)
    val bphigh:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.BPHIGH)
    val sleep:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.Sleep)

    suspend fun insert(dataInput: com.example.start.database.DataInput):Long{
        return inputDao.insertdata(dataInput)
    }

    suspend fun delete(){
        inputDao.deleteAll()
    }

    suspend fun updatetask(task:String,comp:Boolean){
        tasksDao.updatecompletedtask(task,comp)
    }

    suspend fun insertlistnotifications(list:List<Notifications>):List<Long>{
        return notificationsDao.insertlist(list)
    }

    suspend fun markalltasksincomplete(){
        tasksDao.updateall(false)
    }

    suspend fun inserttaskslist(list:List<Dashboard>):List<Long>{
        return dashboardDao.insertlist(list)
    }


}
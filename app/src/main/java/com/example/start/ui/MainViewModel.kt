package com.example.start.ui

import com.example.start.R
import android.app.Application
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.lifecycle.*
import com.example.start.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var alltasks:LiveData<List<Tasks>>
    var alldata: LiveData<List<DataInput>>
    var allsources : LiveData<List<FileData>>
    private var repo : Repository
    var bmi:LiveData<List<FileData>>
    var bplow:LiveData<List<FileData>>
    var bphigh:LiveData<List<FileData>>
    var sleep:LiveData<List<FileData>>

    init{
        val database=InputDatabase.getDatabase(application)
        val inputDao= database.inputDao()
        val tasksDao=database.tasksDao()
        val notificationsDao=database.notificationsDao()
        val dashboardDao= database.dashboardDao()
        repo=Repository(inputDao,tasksDao,notificationsDao,dashboardDao)
        alltasks=repo.alltasks
        allsources=repo.imageallall
        alldata= repo.alldata
        bmi=repo.bmi
        bplow=repo.bplow
        bphigh=repo.bphigh
        sleep=repo.sleep
    }

    fun insert(dataInput: DataInput) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(dataInput)
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO){
        repo.delete()
    }

    fun updatephototask() = viewModelScope.launch(Dispatchers.IO){
        repo.updatetask("Upload a Selfie",true)
    }

    fun insertnotifications(list:List<Notifications>)= viewModelScope.launch(Dispatchers.IO){
        repo.insertlistnotifications(list)
    }

    fun updatetask(task:String) =viewModelScope.launch(Dispatchers.IO) {
        repo.updatetask(task,true)
    }

    fun markalltasksincomplete()=viewModelScope.launch(Dispatchers.IO) {
        repo.markalltasksincomplete()
    }

    fun insertnotifications(){
        val notificationslist= mutableListOf<Notifications>()
        val context=getApplication<Application>()
        val json=context.resources.openRawResource(R.raw.notifications).bufferedReader().use { it.readText() }
        val notifications=JSONObject(json).getJSONArray("notifications")
        for(i in 0 until notifications.length()){
            val notification=notifications.getJSONObject(i)
            val title = notification.getString("title")
            val content = notification.getString("content")
            val hour = notification.getString("hour").toInt()
            val minutes = notification.getString("minutes").toInt()
            val time:Long = (hour*60+minutes)*60000L
            val tasktype = when(notification.getString("type")){
                "Vitals" -> Converters.TasksType.Vitals
                "Tasks"->Converters.TasksType.Tasks
                "Followup" -> Converters.TasksType.Followup
                else -> Converters.TasksType.Goals
            }
            val entrytype = when(notification.getString("entrytype")){
                "Action"->Converters.EntryType.Action
                "Boolean"-> Converters.EntryType.Boolean
                "Number" -> Converters.EntryType.Number
                else -> Converters.EntryType.Scale
            }
            val frequency=when(notification.getString("frequency")) {
                "Daily" -> Converters.Frequency.Daily
                else -> Converters.Frequency.Weekly
            }
            notificationslist.add(Notifications(title = title,frequency = frequency,entryType = entrytype,type = tasktype,notification = content,time = time))
        }
        insertnotifications(notificationslist)
    }

}

package com.example.start.ui.todo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.start.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    var allsources : LiveData<List<FileData>>
    private val picture : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var repo : Repository

    init{

        val inputDao=InputDatabase.getDatabase(application,viewModelScope).inputDao()
        repo=Repository(inputDao)
        picture.value=false
        allsources=repo.imageallall
    }

//    public fun getPicture() : LiveData<Boolean>{
//        return picture
//    }
//
//    public fun setPicture(){
//        Log.println(Log.INFO,"valuepicturebefore",picture.value.toString())
//        picture.value=true
//        Log.println(Log.INFO,"valuepictureafter",picture.value.toString())
//    }

    fun insert(dataInput: DataInput) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(dataInput)
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO){
        repo.delete()
    }

}

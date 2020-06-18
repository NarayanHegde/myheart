package com.example.start.database

import androidx.lifecycle.LiveData
import java.io.DataInput

class Repository(private val inputDao: InputDao) {

    val imageallall:LiveData<List<FileData>> = inputDao.getAllFromSingleType(Converters.Datapath.Image)

    suspend fun insert(dataInput: com.example.start.database.DataInput){
        inputDao.insertdata(dataInput)
    }

    suspend fun delete(){
        inputDao.deleteAll()
    }



}
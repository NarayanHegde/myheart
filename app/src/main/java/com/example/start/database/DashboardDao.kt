package com.example.start.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DashboardDao {

    @Insert
    suspend fun insertone(dashboard: Dashboard):Long

    @Insert
    suspend fun insertlist(list:List<Dashboard>):List<Long>

    @Query("select * from dashboard where type=:type and completed=:bool")
    fun selectcompletedtasks(type:Converters.TasksType,bool: Boolean): LiveData<List<Dashboard>>

}
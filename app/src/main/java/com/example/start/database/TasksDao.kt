package com.example.start.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TasksDao {

    @Insert
    suspend fun inserttask(tasks: Tasks):Long

    @Insert
    suspend fun inserttasks(tasks:List<Tasks>):List<Long>

    @Query("Select * from Tasks")
    fun getalltasks():LiveData<List<Tasks>>

    @Query("Select * from Tasks where type = :type")
    fun gettasksoftype(type:Converters.TasksType):LiveData<List<Tasks>>

    @Query("select id from Tasks where type = :type and task=:task")
    fun gettaskid(type: Converters.TasksType,task:String):Int

    @Query("update Tasks set completed= :bool where task=:task")
    suspend fun updatecompletedtask(task: String, bool:Boolean)

    @Query("select * from Tasks where task=:name")
    suspend fun gettaskwithname(name:String):List<Tasks>

    @Query("delete from tasks where task=:name")
    suspend fun deletetaskwithname(name: String)

    @Update
    suspend fun updatewithid(task:Tasks):Int

    @Query("update Tasks set completed= :bool")
    suspend fun updateall(bool:Boolean)

    @Query("select * from Tasks where completed=:bool")
    suspend fun selectcompleted(bool:Boolean):List<Tasks>

    @Query("delete from tasks")
    suspend fun deleteall()

}
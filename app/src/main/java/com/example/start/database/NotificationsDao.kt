package com.example.start.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationsDao {

    @Insert
    suspend fun insert(notification: Notifications):Long

    @Insert
    suspend fun insertlist(notificationslist:List<Notifications>): List<Long>

    @Query("Select * from notifications ")
    fun selectall():LiveData<List<Notifications>>

    @Query("Update notifications set time = :time where notification=:name ")
    fun updatetime(time: Long,name:String)

    @Query("select * from notifications")
    suspend fun selectalllist():List<Notifications>
}
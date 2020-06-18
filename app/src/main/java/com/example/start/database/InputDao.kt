package com.example.start.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface InputDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertdata (dataInput: DataInput)


    @Query("SELECT data from data_input where datapath= :datatype order by timestamp DESC")
    fun getAllFromSingleType( datatype: Converters.Datapath): LiveData<List<FileData>>

    @Query("DELETE FROM data_input")
    suspend fun deleteAll()

    @Query("DELETE FROM data_input where timestamp between :from and :to")
    suspend fun deleteAllfromto(from: Date,to: Date)

    @Query("DELETE FROM data_input where timestamp > :from ")
    suspend fun deleteAllfrom(from: Date)

    @Query("DELETE FROM data_input where timestamp < :to ")
    suspend fun deleteAllto(to: Date)

    @Query("Select * from data_input where timestamp between :from and :to order by timestamp DESC")
    fun getAllfromto(from: Date, to:Date): LiveData<List<DataInput>>

    @Query("Select * from data_input where timestamp > :from  order by timestamp DESC")
    fun getAllfrom(from: Date):LiveData<List<DataInput>>

    @Query("Select * from data_input where timestamp < :to  order by timestamp DESC")
    fun getAllto(to: Date):LiveData<List<DataInput>>



}
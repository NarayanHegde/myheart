package com.example.start.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

@Entity
class Dashboard (
    @PrimaryKey(autoGenerate = true)val id:Int? = null,
    val timestamp: Long,
    val name:String,
    val type: Converters.TasksType,
    val value: Float?,
    val completed:Boolean?
){
}
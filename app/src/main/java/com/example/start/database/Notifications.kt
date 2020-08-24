package com.example.start.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notifications(
    @PrimaryKey(autoGenerate = true) val id : Int?= null,
    val title:String,
    val notification:String,
    val time:Long,
    val frequency: Converters.Frequency,
    val type:Converters.TasksType,
    val entryType: Converters.EntryType
)  {
}
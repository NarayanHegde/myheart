package com.example.start.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Tasks(
    @PrimaryKey(autoGenerate = true)val id:Int? = null,
    val task:String,
    val type: Converters.TasksType,
    val entrytype:Converters.EntryType,
    val target: Int?,
    val frequency:Converters.Frequency,
    val completed:Boolean
) {
}
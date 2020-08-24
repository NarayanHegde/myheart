package com.example.start.database

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "data_input",indices = arrayOf(Index(value = ["timestamp"])))
class DataInput(
    @PrimaryKey(autoGenerate = true)val id:Int?=null,
    val timestamp : Date,
    val healthvital:Converters.Indicator,
    val datapath :Converters.Datapath,
    val data : String,
    val source : Boolean,
    val modelname:String?,
    val preprocessdata:String?
) {
}
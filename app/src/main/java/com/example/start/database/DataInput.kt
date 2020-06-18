package com.example.start.database

import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "data_input")
class DataInput(
    @PrimaryKey val timestamp : Date,
    val healthvital:Converters.Indicator,
    val datapath :Converters.Datapath,
    val data : String,
    val source : Boolean,
    val modelname:String,
    val preprocessdata:String?
) {
}
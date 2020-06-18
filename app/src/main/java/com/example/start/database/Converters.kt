package com.example.start.database

import androidx.room.TypeConverter
import java.util.*

class Converters {

    enum class Indicator {
        BMI,
        Smoke
    }

    enum class Datapath {
        Image,
        Weight,
        Height,
        Age
    }

    @TypeConverter
    fun toIndicator(value:Int):Indicator?{
        return Indicator.values()[value]
    }

    @TypeConverter
    fun fromindicator(indicator: Indicator):Int?{
        return indicator.ordinal
    }

    @TypeConverter
    fun fromDate(date: Date):Long?{
        return date.time
    }

    @TypeConverter
    fun toDate(long: Long):Date?{
        return Date(long)
    }

    @TypeConverter
    fun fromDatapath(datapath:Datapath):Int?{
        return datapath.ordinal
    }

    @TypeConverter
    fun toDatapath(int:Int):Datapath?{
        return Datapath.values()[int]
    }


}
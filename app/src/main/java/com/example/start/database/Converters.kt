package com.example.start.database

import androidx.room.TypeConverter
import java.util.*

class Converters {

    enum class Indicator {
        General,
        BMI,
        Smoke,
        SocioEconomic
    }

    enum class Datapath {
        BPLOW,
        BPHIGH,
        HDL,
        LDL,
        TotalCholesterol,
        HbA1c,
        Image,
        Weight,
        Height,
        Age,
        BMI,
        Gender,
        Sleep,
        Stress,
        Support,
        Smoke,
        History,
        Medication
    }

    enum class TasksType{
        Vitals,
        Tasks,
        Followup,
        Goals
    }

    enum class EntryType{
        Number,
        Scale,
        Boolean,
        Action
    }

    enum class Frequency{
        Weekly,
        Daily
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

    @TypeConverter
    fun fromtasktype(tasksType: TasksType): Int?{
        return tasksType.ordinal
    }

    @TypeConverter
    fun totaskstype(int: Int):TasksType?{
        return TasksType.values()[int]
    }

    @TypeConverter
    fun fromentrytype(entryType: EntryType): Int?{
        return entryType.ordinal
    }

    @TypeConverter
    fun toentrytype(int: Int):EntryType?{
        return EntryType.values()[int]
    }

    @TypeConverter
    fun fromfrequency(frequency:Frequency):Int?{
        return frequency.ordinal
    }

    @TypeConverter
    fun tofrequency(int: Int): Frequency?{
        return Frequency.values()[int]
    }
}
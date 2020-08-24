package com.example.start.database

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(DataInput::class,Tasks::class,Notifications::class,Dashboard::class),version=1,exportSchema = false)
@TypeConverters(Converters::class)
public abstract  class InputDatabase() : RoomDatabase (){

    abstract fun inputDao() : InputDao
    abstract fun tasksDao() : TasksDao
    abstract fun notificationsDao() : NotificationsDao
    abstract fun dashboardDao(): DashboardDao

    companion object {

        @Volatile
        private var INSTANCE : InputDatabase? =null

        public fun getDatabase( context: Context
        ): InputDatabase{
            val tempinstance = INSTANCE
            if(tempinstance != null){
                return tempinstance
            }
            synchronized(this){

                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    InputDatabase::class.java,
                    "photo_database")
                    .build()
//                Log.d("databasecreated","true")
                INSTANCE=instance
                return instance
            }
        }

    }


}
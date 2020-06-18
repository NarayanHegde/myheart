package com.example.start.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(DataInput::class),version=1,exportSchema = false)
@TypeConverters(Converters::class)
public abstract  class InputDatabase() : RoomDatabase (){

    abstract fun inputDao() : InputDao

    private class DataCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let{
                scope.launch{
                    it.inputDao().deleteAll()
                }
            }

        }
    }

    companion object {

        @Volatile
        private var INSTANCE : InputDatabase? =null

        public fun getDatabase( context: Context
                                , scope: CoroutineScope
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
//                    .addCallback(DataCallback(scope))
                    .build()
                INSTANCE=instance
                return instance
            }
        }

    }


}
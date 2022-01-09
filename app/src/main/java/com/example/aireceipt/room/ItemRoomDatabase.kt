package com.example.aireceipt.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Item::class,Bill::class,AllBills::class], version = 3,exportSchema = false)
@TypeConverters(converter::class)
abstract class ItemRoomDatabase:RoomDatabase() {
    abstract fun itemDao():ItemDao

    companion object{
        @Volatile
        private var INSTANCE:ItemRoomDatabase?=null
        fun getDatabase(context:Context):ItemRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "bill_databse"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}
package com.example.contacts.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contacts.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun daoContact(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE : ContactDatabase? = null
        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "Contact_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
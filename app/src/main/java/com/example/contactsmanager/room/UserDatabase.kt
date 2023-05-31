package com.example.contactsmanager.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDAO : UserDAO

    // Singleton - creating a single object for database
    companion object{
        @Volatile
        private var INSTANCE : UserDatabase ?= null
        fun getInstance(context : Context) : UserDatabase{
            var instance = INSTANCE
            // Checking if database object is created
            if(instance == null){
                // Creating the database object
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).build()
            }
            return instance
        }
    }
}
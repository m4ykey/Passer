package com.example.password_manager_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.password_manager_room.data.Account

@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase : RoomDatabase(){

    abstract val accountDao : AccountDao

    companion object{
        @Volatile
        var INSTANCE : AccountDatabase ?= null

        @Synchronized
        fun getDatabase(context: Context) : AccountDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AccountDatabase::class.java,
                    "account_database"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}
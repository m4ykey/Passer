package com.example.password_manager_room.di

import android.app.Application
import android.content.Context
import com.example.password_manager_room.database.AccountDao
import com.example.password_manager_room.database.AccountDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun getAppDatabase(application : Application) : AccountDatabase{
        return AccountDatabase.getDatabase(application)
    }

    @Singleton
    @Provides
    fun getDao(accountDatabase: AccountDatabase) : AccountDao {
        return accountDatabase.accountDao
    }
}
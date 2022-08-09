package com.example.password_manager_room.database

import androidx.room.*
import com.example.password_manager_room.data.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account ORDER BY id DESC")
    fun allAccount() : Flow<List<Account>>

    @Query("SELECT * FROM account WHERE company LIKE '%' ||:searchQuery||'%'")
    fun searchInAccount(searchQuery : String) : Flow<List<Account>>

    @Query("DELETE FROM account")
    suspend fun deleteAllAccounts()
}
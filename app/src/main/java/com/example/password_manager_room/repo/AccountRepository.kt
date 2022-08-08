package com.example.password_manager_room.repo

import com.example.password_manager_room.data.Account
import com.example.password_manager_room.database.AccountDao
import com.example.password_manager_room.database.AccountDatabase
import kotlinx.coroutines.flow.Flow

class AccountRepository(
    accountDatabase: AccountDatabase
) {

    val accountDao = accountDatabase.accountDao

    suspend fun upsertNote(account: Account) = accountDao.upsertAccount(account)

    suspend fun deleteAccount(account: Account) = accountDao.deleteAccount(account)

    fun allAccount() : Flow<List<Account>> = accountDao.allAccount()

    fun searchInAccount(searchQuery : String) : Flow<List<Account>> = accountDao.searchInAccount(searchQuery)

    suspend fun deleteAllAccounts() = accountDao.deleteAllAccounts()
}
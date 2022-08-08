package com.example.password_manager_room.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.password_manager_room.data.Account
import com.example.password_manager_room.repo.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val accounts = accountRepository.allAccount()
    private val _searchAccount = MutableStateFlow<List<Account>>(emptyList())
    val searchAccount : StateFlow<List<Account>> = _searchAccount

    fun upsertAccount(account: Account){
        viewModelScope.launch {
            accountRepository.upsertNote(account)
        }
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            accountRepository.deleteAccount(account)
        }
    }

    fun searchInAccount(searchQuery: String){
        viewModelScope.launch {
            accountRepository.searchInAccount(searchQuery).collect{ accountList ->
                _searchAccount.emit(accountList)
            }
        }
    }
}
package com.example.password_manager_room.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.password_manager_room.repo.AccountRepository

class AccountViewModelProvider(
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(accountRepository) as T
    }
}
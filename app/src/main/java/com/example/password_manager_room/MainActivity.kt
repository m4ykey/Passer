package com.example.password_manager_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.password_manager_room.database.AccountDatabase
import com.example.password_manager_room.mvvm.AccountViewModel
import com.example.password_manager_room.mvvm.AccountViewModelProvider
import com.example.password_manager_room.repo.AccountRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel : AccountViewModel by lazy {
        val database = AccountDatabase.getDatabase(this)
        val accountRepository = AccountRepository(database)
        val accountProviderFactory = AccountViewModelProvider(accountRepository)
        ViewModelProvider(this, accountProviderFactory)[AccountViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)
    }
}
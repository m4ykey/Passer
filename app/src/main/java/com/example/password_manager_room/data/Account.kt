package com.example.password_manager_room.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val email : String,
    val company : String,
    val password : String
) : Parcelable

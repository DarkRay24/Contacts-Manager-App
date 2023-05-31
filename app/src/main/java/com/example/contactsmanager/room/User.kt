package com.example.contactsmanager.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id : Int,
    @ColumnInfo(name = "user_name")
    var name : String,
    @ColumnInfo(name = "user_email")
    var email : String
    )

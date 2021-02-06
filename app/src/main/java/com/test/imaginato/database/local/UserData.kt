package com.test.imaginato.database.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User @JvmOverloads constructor(
    @ColumnInfo(name = "X-Acc") var xAcc: String = "",
    @ColumnInfo(name = "userName") var userName: String = "false",
    @PrimaryKey @ColumnInfo(name = "userId") var userId: Int
)
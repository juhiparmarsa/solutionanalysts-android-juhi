package com.test.imaginato.database.local


import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * The Room Database that contains the User table.
 *
 *
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authDao(): AuthDao
}
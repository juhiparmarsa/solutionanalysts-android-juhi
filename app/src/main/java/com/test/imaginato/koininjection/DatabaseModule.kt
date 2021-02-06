package com.test.imaginato.koininjection


import androidx.room.Room
import com.test.imaginato.database.local.AppDatabase
import com.test.imaginato.database.local.AuthLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import com.test.imaginato.utils.Constants.DATABASE_NAME
val dataBaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, DATABASE_NAME
        ).build()
    }

    single {
        val appDatabase: AppDatabase = get()
        appDatabase.authDao()
    }

    single {
        AuthLocalDataSource(get())
    }
}
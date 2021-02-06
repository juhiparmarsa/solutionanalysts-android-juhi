package com.test.imaginato.koininjection


import android.content.Context
import android.content.SharedPreferences
import com.test.imaginato.utils.Constants.PREFERENCE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            PREFERENCE,
            Context.MODE_PRIVATE
        )
    }

}
package com.test.imaginato.koininjection


import com.test.imaginato.base.AuthRepository
import org.koin.dsl.module.module

val repositoryModule = module {


    single {
        AuthRepository(
            get(),
            get()
        )//it will take two argument ApiInterface and PreferenceManager
    }

}
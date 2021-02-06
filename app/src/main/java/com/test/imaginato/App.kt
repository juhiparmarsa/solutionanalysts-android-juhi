package com.test.imaginato
import android.app.Application
import com.test.imaginato.koininjection.*
import org.koin.android.ext.android.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        iniKoinModules()
    }

    override fun onTerminate() {
        super.onTerminate()
        if (instance != null) {
            instance = null
        }
    }

    companion object {
        var instance: App? = null
    }
    private fun iniKoinModules() {
        startKoin(
            this,
            listOf(appModule, repositoryModule, viewModelModule, networkModule, dataBaseModule)
        )
    }


}
package com.test.imaginato.koininjection


import android.util.Log

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.imaginato.database.remote.ApiInterface
import com.test.imaginato.utils.ApiConfig
import com.test.imaginato.utils.getIMEINumber
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Dependency: ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiInterface::class.java)
    }

    // Dependency: HttpLoggingInterceptor
    single<Interceptor>(name = "INTERCEPTOR_LOGGING") {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single(name = "INTERCEPTOR_HEADER") {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader(
                        ApiConfig.HEADER_CONTENT_TYPE,
                        ApiConfig.HEADER_CONTENT_TYPE_JSON
                    ).addHeader(
                        ApiConfig.HEADER_NAME_IMSI, getIMEINumber()
                    ).addHeader(
                        ApiConfig.HEADER_NAME_IMEI, getIMEINumber()
                    ).build()
            )
        }
    }

    // Dependency: OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(get<Interceptor>("INTERCEPTOR_LOGGING"))
            .addInterceptor(get<Interceptor>("INTERCEPTOR_HEADER"))
            .build()
    }
}
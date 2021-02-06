package com.test.imaginato.database.remote

import com.test.imaginato.ui.signin.model.RequestData
import com.test.imaginato.ui.signin.model.ResponseData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("login")
    fun loginAsync(@Body modelReq: RequestData): Deferred<Response<ResponseData>>

}
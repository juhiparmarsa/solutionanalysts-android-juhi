package com.test.imaginato.database.remote

import com.test.imaginato.utils.ApiConfig
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException

open class BaseRemoteDataSource {

    suspend fun <T : BaseResult> apiCall(call: suspend () -> Response<T>): BaseResult? {
        val result: Resource<T> = safeApiResult(call)
        var data: BaseResult? = null

        when (result) {
            is Resource.Success -> data = result.data
            is Resource.Error -> data = result.error
        }
        return data
    }

    private suspend fun <T : BaseResult> safeApiResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                if (response.body() != null && response.body() is BaseResult) {

                    val baseResult = response.body() as BaseResult
                    baseResult.headers = response.headers()
                    return if (response.code() == 200) {
                        Resource.Success(response.body()!!)
                    } else {
                        val baseError = BaseError()
                        Resource.Error(baseError)
                    }
                } else {
                    val baseError = BaseError()
                    baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                    return Resource.Error(baseError)
                }
            } else {
                if (response.errorBody() != null) {

                    if (response.headers()["Content-Type"]?.contains("application/json")!!) {//check if response is in json format

                        val errorString = response.errorBody()?.string()
                        val baseError = BaseError()
                        if (errorString?.isNullOrEmpty() == false) {
                            val jsonResponse = JSONObject(errorString)
                            try {
                                baseError.code = response.code().toString()
                                baseError.message = jsonResponse.get("error").toString()

                            } catch (e: Exception) {
                                baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                                baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                            }
                        } else {
                            baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                            baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                        }
                        return Resource.Error(baseError)

                    } else {
                        val baseError = BaseError()
                        baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                        baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                        return Resource.Error(baseError)
                    }
                } else {
                    val baseError = BaseError()
                    baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                    return Resource.Error(baseError)
                }

            }
        } catch (error: Exception) {
            return when (error) {
                is ConnectException -> {
                    val baseError = BaseError()
                    baseError.code = ApiConfig.TIME_OUT_CONNECTION_STATUS
                    baseError.message = ApiConfig.TIME_OUT_CONNECTION
                    Resource.Error(baseError)
                }

                else -> {
                    val baseError = BaseError()
                    baseError.code = ApiConfig.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConfig.SOMETHING_WRONG_ERROR
                    Resource.Error(baseError)
                }
            }

        }

    }
}
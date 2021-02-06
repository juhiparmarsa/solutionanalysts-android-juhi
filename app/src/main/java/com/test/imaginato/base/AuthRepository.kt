package com.test.imaginato.base


import androidx.lifecycle.MutableLiveData
import com.test.imaginato.database.local.AuthLocalDataSource
import com.test.imaginato.database.local.User
import com.test.imaginato.database.remote.*
import com.test.imaginato.ui.signin.model.RequestData
import com.test.imaginato.ui.signin.model.ResponseData
import com.test.imaginato.utils.ApiConfig


class AuthRepository constructor(
    private var apiInterface: ApiInterface,
    private var authLocalDataSource: AuthLocalDataSource
) : BaseRemoteDataSource() {

    suspend fun login(
        loginLiveData: MutableLiveData<Resource<ResponseData>>,
        modelReq: RequestData
    ) {
        loginLiveData.postValue(Resource.Loading(DataLoader.LOADING_ALL))
        val result = apiCall(call = { apiInterface.loginAsync(modelReq).await() })
        if (result is ResponseData) {
            saveUserDataLocally(result)
            loginLiveData.postValue(Resource.Success(result))
        } else if (result is BaseError) {
            loginLiveData.postValue(Resource.Error(result))
        }
    }

    private suspend fun saveUserDataLocally(result: ResponseData) {
        val user = User(
            userId = result.user?.userId ?: 0,
            userName = result.user?.userName ?: "",
            xAcc = result.headers?.get(ApiConfig.X_ACC_HEADER) ?: ""
        )
        authLocalDataSource.saveUser(user)

    }
}
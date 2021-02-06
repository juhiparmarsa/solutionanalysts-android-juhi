package com.test.imaginato.ui.signin.viewmodel


import android.app.Application
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.imaginato.R
import com.test.imaginato.base.AuthRepository
import com.test.imaginato.base.SingleLiveEvent
import com.test.imaginato.database.remote.Resource
import com.test.imaginato.ui.signin.model.RequestData
import com.test.imaginato.ui.signin.model.ResponseData
import com.test.imaginato.utils.*
import kotlinx.coroutines.launch

class SignInViewModel(application: Application, private val authRepository: AuthRepository) :
    AndroidViewModel(application) {

    var userName: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }
    var password: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" }

    var userNameError = MutableLiveData<String?>().apply { value = "" }
    var passwordError = MutableLiveData<String?>().apply { value = "" }

    val showSnackBar = SingleLiveEvent<Int>()
    val loginLiveData = SingleLiveEvent<Resource<ResponseData>>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun onLoginClick(view: View) {
        if (isNetworkAvailable(getApplication())) {
            KeyboardUtils.hideKeyboard(view)
            if (validateFields()) {
                signInApiCall()
            }
        } else {
            showSnackBar.postValue(R.string.error_message_network)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getOnEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onLoginClick(v)
                true
            } else {
                false
            }
        }
    }

    //validate login fields
    private fun validateFields(): Boolean {

        var isValidate = true

        val resultEmail = isValidUserName(userName.value)
        isValidate = setEditTextError(isValidate, resultEmail, userNameError)

        val resultPassword = isValidPassword(password.value)
        isValidate = setEditTextError(isValidate, resultPassword, passwordError)
        return isValidate
    }

    private fun signInApiCall() {
        viewModelScope.launch {
            authRepository.login(
                loginLiveData,
                RequestData(username = userName.value, password = password.value)
            )
        }

    }
    //  Clear filed when login successfully
    fun clearInputValue() {
        userName.value = ""
        password.value = ""
    }
}
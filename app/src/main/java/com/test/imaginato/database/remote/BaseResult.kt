package com.test.imaginato.database.remote

import androidx.annotation.Keep
import okhttp3.Headers

@Keep
open class BaseResult {
    var message: String? = null
    var code: String? = null
    var errorCode: String? = null
    var errorMessage: String? = null
    var headers: Headers? = null
}
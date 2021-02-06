package com.test.imaginato.ui.signin.model
import androidx.annotation.Keep
import com.test.imaginato.database.remote.BaseResult

@Keep
class ResponseData(
    val user: User? = null
) : BaseResult() {
    @Keep
    data class User(
        val userName: String? = null,
        val userId: Int? = null
    )
}
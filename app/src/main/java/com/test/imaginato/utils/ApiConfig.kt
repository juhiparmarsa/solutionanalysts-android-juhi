package com.test.imaginato.utils

object ApiConfig {
    const val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/api/"
    const val X_ACC_HEADER = "X-Acc"

    const val HEADER_NAME_IMSI = "IMSI"
    const val HEADER_NAME_IMEI = "IMEI"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_CONTENT_TYPE_JSON = "application/json"

    const val TIME_OUT_CONNECTION = "Cannot connect to server.\nPlease try again later."
    const val TIME_OUT_CONNECTION_STATUS = "504"

    const val SOMETHING_WRONG_ERROR = "Something went wrong!!\nPlease try again later."
    const val SOMETHING_WRONG_ERROR_STATUS = "505"
}